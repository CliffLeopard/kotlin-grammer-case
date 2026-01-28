#!/usr/bin/env python3
"""
统计 Kotlin 样本代码的语法覆盖情况
生成 STATISTICS.md 文档
"""

import os
import re
from pathlib import Path
from collections import defaultdict
from typing import Dict, List, Set

# 项目根目录
# stats.py 在根目录 tools/ 下
SCRIPT_DIR = Path(__file__).parent
ROOT = SCRIPT_DIR.parent
SAMPLES_DIR = ROOT / "src" / "main" / "kotlin"
OUTPUT_FILE = ROOT / "docs" / "STATISTICS.md"


class KotlinStats:
    def __init__(self):
        self.files: List[Path] = []
        self.total_lines = 0
        self.total_files = 0
        self.packages: Set[str] = set()
        
        # 语法统计
        self.classes = 0
        self.interfaces = 0
        self.enums = 0
        self.objects = 0
        self.functions = 0
        self.properties = 0
        self.constructors = 0
        self.extensions = 0
        self.generics = 0
        self.annotations = 0
        
        # CALLS 关系统计
        self.function_calls = 0
        self.method_calls = 0
        self.extension_calls = 0
        self.super_calls = 0
        self.this_calls = 0
        self.lambda_calls = 0
        self.operator_calls = 0
        self.property_access = 0
        self.constructor_calls = 0
        
        # 导入统计
        self.imports: Dict[str, int] = defaultdict(int)
        
        # 文件结构
        self.file_structure: Dict[str, List[str]] = defaultdict(list)
    
    def scan_file(self, file_path: Path):
        """扫描单个文件"""
        try:
            with open(file_path, 'r', encoding='utf-8') as f:
                content = f.read()
                lines = content.split('\n')
                self.total_lines += len(lines)
                self.total_files += 1
                self.files.append(file_path)
                
                # 提取包名
                package_match = re.search(r'^package\s+([\w.]+)', content, re.MULTILINE)
                if package_match:
                    package = package_match.group(1)
                    self.packages.add(package)
                    rel_path = file_path.relative_to(SAMPLES_DIR)
                    self.file_structure[package].append(str(rel_path))
                
                # 统计语法元素
                self._count_syntax(content)
                
                # 统计导入
                self._count_imports(content)
                
        except Exception as e:
            print(f"Error scanning {file_path}: {e}")
    
    def _count_syntax(self, content: str):
        """统计语法元素"""
        # 类
        self.classes += len(re.findall(r'\b(class|data class|sealed class|open class|abstract class|inner class)\s+\w+', content))
        
        # 接口
        self.interfaces += len(re.findall(r'\b(interface|sealed interface)\s+\w+', content))
        
        # 枚举
        self.enums += len(re.findall(r'\benum\s+class\s+\w+', content))
        
        # 对象
        self.objects += len(re.findall(r'\bobject\s+\w+', content))
        
        # 函数（顶层函数）
        self.functions += len(re.findall(r'^\s*fun\s+\w+', content, re.MULTILINE))
        
        # 属性（顶层属性）
        self.properties += len(re.findall(r'^\s*(val|var|const)\s+\w+', content, re.MULTILINE))
        
        # 构造函数
        self.constructors += len(re.findall(r'\bconstructor\s*\(', content))
        
        # 扩展函数
        self.extensions += len(re.findall(r'fun\s+[\w<>.]+\s*\.\s*\w+', content))
        
        # 泛型
        self.generics += len(re.findall(r'<\w+[:\s\w,<>?*]*>', content))
        
        # 注解
        self.annotations += len(re.findall(r'@\w+', content))
    
    def _count_imports(self, content: str):
        """统计导入"""
        import_pattern = r'^import\s+([\w.]+(?:\.[\w*]+)?)'
        for match in re.finditer(import_pattern, content, re.MULTILINE):
            import_path = match.group(1)
            # 提取包名（去掉最后的类名）
            if '.' in import_path:
                parts = import_path.rsplit('.', 1)
                if parts[0]:
                    self.imports[parts[0]] += 1
    
    def _estimate_calls(self, content: str):
        """估算调用关系（简单启发式）"""
        # 函数调用（简单模式）
        self.function_calls += len(re.findall(r'\b\w+\s*\([^)]*\)', content))
        
        # super 调用
        self.super_calls += len(re.findall(r'\bsuper\.\w+', content))
        
        # this 调用
        self.this_calls += len(re.findall(r'\bthis\.\w+', content))
        
        # lambda 调用
        self.lambda_calls += len(re.findall(r'\{\s*[^}]*\w+\s*\(', content))
        
        # 操作符调用
        self.operator_calls += len(re.findall(r'\b\w+\s*[+\-*/%<>!=]+\s*\w+', content))
    
    def generate_report(self) -> str:
        """生成统计报告"""
        lines = []
        from datetime import datetime
        lines.append("# Kotlin 语法样本代码统计信息\n")
        lines.append(f"生成时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}\n")
        lines.append("---\n\n")
        
        # 总体统计
        lines.append("## 总体统计\n\n")
        lines.append(f"- **总文件数**: {self.total_files}\n")
        lines.append(f"- **总行数**: {self.total_lines}\n")
        lines.append(f"- **包数量**: {len(self.packages)}\n")
        lines.append(f"- **导入的包数量**: {len(self.imports)}\n\n")
        
        # 文件列表
        lines.append("## 文件列表\n\n")
        for package in sorted(self.packages):
            lines.append(f"### {package}\n\n")
            for file_path in sorted(self.file_structure[package]):
                lines.append(f"- `{file_path}`\n")
            lines.append("\n")
        
        # 语法元素统计
        lines.append("## 语法元素统计\n\n")
        lines.append("| 类型 | 数量 |\n")
        lines.append("|------|------|\n")
        lines.append(f"| 类 (class) | {self.classes} |\n")
        lines.append(f"| 接口 (interface) | {self.interfaces} |\n")
        lines.append(f"| 枚举 (enum) | {self.enums} |\n")
        lines.append(f"| 对象 (object) | {self.objects} |\n")
        lines.append(f"| 函数 (function) | {self.functions} |\n")
        lines.append(f"| 属性 (property) | {self.properties} |\n")
        lines.append(f"| 构造函数 (constructor) | {self.constructors} |\n")
        lines.append(f"| 扩展函数 (extension) | {self.extensions} |\n")
        lines.append(f"| 泛型使用 (generic) | {self.generics} |\n")
        lines.append(f"| 注解 (annotation) | {self.annotations} |\n\n")
        
        # 包依赖关系
        lines.append("## 包导入关系 (IMPORTS)\n\n")
        lines.append("| 源包 | 导入次数 |\n")
        lines.append("|------|----------|\n")
        for pkg, count in sorted(self.imports.items(), key=lambda x: -x[1]):
            lines.append(f"| `{pkg}` | {count} |\n")
        lines.append("\n")
        
        # 覆盖的语法点
        lines.append("## 覆盖的语法点\n\n")
        syntax_points = [
            "类和继承 (class, open, abstract, sealed)",
            "接口实现 (interface, sealed interface)",
            "枚举类 (enum class)",
            "单例对象 (object)",
            "伴生对象 (companion object)",
            "数据类 (data class)",
            "值类 (value class)",
            "泛型类和函数 (generics)",
            "协变和逆变 (out, in)",
            "扩展函数和属性 (extension)",
            "高阶函数 (higher-order functions)",
            "Lambda 表达式",
            "函数引用 (function reference)",
            "内联函数 (inline, noinline, crossinline)",
            "委托 (delegation)",
            "属性委托 (lazy, observable, vetoable)",
            "操作符重载 (operator overloading)",
            "作用域函数 (let, run, with, apply, also)",
            "协程 (coroutines, suspend)",
            "Flow (flow, collect)",
            "注解 (annotations)",
            "反射 (reflection)",
            "控制流 (if, when, for, while)",
            "异常处理 (try-catch-finally)",
            "递归和尾递归 (tailrec)",
            "多态调用 (polymorphism)",
            "super 调用",
            "this 调用",
            "链式调用",
            "条件调用",
            "循环中的调用",
            "异常处理中的调用",
        ]
        for point in syntax_points:
            lines.append(f"- {point}\n")
        lines.append("\n")
        
        # CALLS 关系重点场景
        lines.append("## CALLS 关系重点场景\n\n")
        call_scenarios = [
            "顶层函数调用",
            "成员方法调用",
            "扩展函数调用",
            "构造函数调用",
            "super 方法调用",
            "this 方法调用",
            "Lambda 中的调用",
            "高阶函数回调中的调用",
            "操作符调用 (+, -, *, /, [], invoke 等)",
            "属性访问器调用 (getter/setter)",
            "委托调用",
            "协程中的调用",
            "Flow 中的调用",
            "作用域函数中的调用",
            "递归调用",
            "相互递归调用",
            "多态调用",
            "泛型方法调用",
            "内联函数中的调用",
            "反射调用",
            "函数引用调用",
            "SAM 转换调用",
        ]
        for scenario in call_scenarios:
            lines.append(f"- {scenario}\n")
        lines.append("\n")
        
        # 文件详细列表
        lines.append("## 详细文件列表\n\n")
        for file_path in sorted(self.files):
            rel_path = file_path.relative_to(SAMPLES_DIR)
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    file_lines = len(f.readlines())
                lines.append(f"- `{rel_path}` ({file_lines} 行)\n")
            except:
                lines.append(f"- `{rel_path}`\n")
        
        return '\n'.join(lines)


def main():
    """主函数"""
    stats = KotlinStats()
    
    # 扫描所有 Kotlin 文件
    if SAMPLES_DIR.exists():
        for kt_file in SAMPLES_DIR.rglob("*.kt"):
            stats.scan_file(kt_file)
    else:
        print(f"错误: 样本目录不存在: {SAMPLES_DIR}")
        return
    
    # 生成报告
    report = stats.generate_report()
    
    # 确保输出目录存在
    OUTPUT_FILE.parent.mkdir(parents=True, exist_ok=True)
    
    # 写入文件
    with open(OUTPUT_FILE, 'w', encoding='utf-8') as f:
        f.write(report)
    
    print(f"统计完成！")
    print(f"- 扫描文件数: {stats.total_files}")
    print(f"- 总行数: {stats.total_lines}")
    print(f"- 输出文件: {OUTPUT_FILE}")


if __name__ == "__main__":
    main()
