
### NodeType

| Label                | Properties                                                                        |
| -------------------- | --------------------------------------------------------------------------------- |
| Project              | `{name: string}`                                                                  |
| Package              | `{qualified_name: string, name: string, path: string}`                            |
| Folder               | `{path: string, name: string}`                                                    |
| File                 | `{path: string, name: string, extension: string}`                                 |
| Module               | `{qualified_name: string, name: string, path: string}`                            |
| Class                | `{qualified_name: string, name: string, decorators: list[string]}`                |
| Function             | `{qualified_name: string, name: string, decorators: list[string]}`                |
| Method               | `{qualified_name: string, name: string, decorators: list[string]}`                |
| Interface            | `{qualified_name: string, name: string}`                                          |
| Enum                 | `{qualified_name: string, name: string}`                                          |
| Type                 | `{qualified_name: string, name: string}`                                          |
| Union                | `{qualified_name: string, name: string}`                                          |
| ModuleInterface      | `{qualified_name: string, name: string, path: string}`                            |
| ModuleImplementation | `{qualified_name: string, name: string, path: string, implements_module: string}` |
| ExternalPackage      | `{name: string, version_spec: string}`                                            |

### RelationShip

| Source                   | Relationship        | Target               |
| ------------------------ | ------------------- | -------------------- |
| Project, Package, Folder | CONTAINS_PACKAGE    | Package              |
| Project, Package, Folder | CONTAINS_FOLDER     | Folder               |
| Project, Package, Folder | CONTAINS_FILE       | File                 |
| Project, Package, Folder | CONTAINS_MODULE     | Module               |
| Module                   | DEFINES             | Class, Function      |
| Class                    | DEFINES_METHOD      | Method               |
| Module                   | IMPORTS             | Module               |
| Module                   | EXPORTS             | Class, Function      |
| Module                   | EXPORTS_MODULE      | ModuleInterface      |
| Module                   | IMPLEMENTS_MODULE   | ModuleImplementation |
| Class                    | INHERITS            | Class                |
| Class                    | IMPLEMENTS          | Interface            |
| Method                   | OVERRIDES           | Method               |
| ModuleImplementation     | IMPLEMENTS          | ModuleInterface      |
| Project                  | DEPENDS_ON_EXTERNAL | ExternalPackage      |
| Function, Method         | CALLS               | Function, Method     |

