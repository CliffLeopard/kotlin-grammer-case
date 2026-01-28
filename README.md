# Kotlin 语法解析测试用例项目

本项目用于测试 code-graph-rag 对 Kotlin 的语法解析能力，生成全面的 Kotlin 语法样本代码和统计信息。

## 项目结构

```
.
├── rule.md                          # 项目规则和应用场景说明
├── src/main/kotlin/                 # 根模块源码
│   ├── Main.kt
│   └── cases/                       # Kotlin 语法样本源码（按主题组织）
│       ├── calls/                   # 调用关系场景
│       ├── classes/                 # 类和继承
│       ├── collections/             # 集合操作
│       ├── controlflow/             # 控制流
│       ├── core/                    # 核心语法（注解、导入等）
│       ├── coroutines/              # 协程
│       ├── delegation/              # 委托
│       ├── extensions/              # 扩展函数
│       ├── fp/                      # 函数式编程
│       ├── generics/                # 泛型
│       ├── inline/                  # 内联函数
│       ├── modules/                 # 模块系统
│       ├── objects/                 # 对象表达式和单例
│       ├── operators/               # 操作符重载
│       ├── reflection/              # 反射
│       ├── scope/                   # 作用域函数
│       └── types/                   # 类型系统
├── tools/
│   └── stats.py                     # 统计脚本
└── docs/
    ├── README.md                    # 文档说明
    └── STATISTICS.md                # 统计信息（自动生成）
```

## 目标

本项目旨在：

1. **生成完整的测试用例供解析**
   - 覆盖 Kotlin 的主要语法特性
   - 重点构造各种 CALLS 关系场景

2. **尽量完整覆盖 Kotlin 的所有语法**
   - 类和继承、接口、枚举
   - 函数、扩展函数、高阶函数
   - 泛型、委托、协程
   - 操作符重载、作用域函数
   - 控制流、异常处理
   - 等等...

3. **重点在于 CALL 关系的各种场景**
   - 顶层函数调用
   - 成员方法调用
   - 扩展函数调用
   - super/this 调用
   - Lambda 中的调用
   - 操作符调用
   - 属性访问器调用
   - 委托调用
   - 递归调用
   - 多态调用
   - 等等...

4. **生成各种统计信息，以便验证**
   - 文件数量、代码行数
   - 语法元素统计
   - 包导入关系
   - CALLS 场景覆盖情况

## 统计信息

查看详细的统计信息：

```bash
cat docs/STATISTICS.md
```

或重新生成：

```bash
python3 tools/stats.py
```

## 当前统计概览

- **总文件数**: 20
- **总行数**: 1438+
- **包数量**: 17
- **语法元素**: 类 52+、接口 10+、函数 123+、扩展函数 5+、泛型使用 58+ 等

详细统计请查看 `docs/STATISTICS.md`。

## 使用说明

### 查看样本代码

所有样本代码位于根模块 `src/main/kotlin/cases/` 目录下，按语法主题组织。

### 生成统计信息

运行统计脚本：

```bash
python3 tools/stats.py
```

统计结果会输出到 `docs/STATISTICS.md`。

### 解析测试

使用 code-graph-rag 解析这些样本代码，验证是否能正确识别：

- **Node 类型**: Project, Package, Folder, File, Module, Class, Function, Method, Interface, Enum, Type 等
- **Relationship 类型**: CONTAINS_*, DEFINES, IMPORTS, EXPORTS, INHERITS, IMPLEMENTS, OVERRIDES, CALLS, DEPENDS_ON_EXTERNAL 等

特别是 **CALLS** 关系的各种场景，这是本项目的重点测试内容。


### 目标

- 尽量覆盖 Kotlin 常见/关键语法点（类型系统、声明、表达式、控制流、协程、泛型、内联等）
- **重点覆盖 CALL 关系**的各种场景：顶层函数、成员函数、扩展函数、构造器、lambda、函数引用、默认参数、vararg、SAM、`super`/`this`、`invoke`、委托、属性访问器等
- 通过多包、多文件 import 形成稳定的 `IMPORTS`、`DEFINES`、`EXPORTS` 等关系


### 生成统计

在项目根目录运行：

```bash
python3 tools/stats.py
```

