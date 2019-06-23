# avro-kotlin-maven-plugin
Maven Plugin to generate Kotlin Source code of Avro SpecificRecord classes based on Avro Schema definitions.

The plugin is tested against Avro data created by the org.apache.avro:avro-maven-plugin:1.9.0 plugin (and vice versa, tested that classes created by this plugin can read data serialized by the equivalent Kotlin classes). 

Related resources:
* [avro-kotlin-maven-plugin Git Repository](https://github.com/fuchsst/avro-kotlin-maven-plugin.git)
* [avro-kotlin-maven-plugin Issue Tracker](https://github.com/fuchsst/avro-kotlin-maven-plugin/issues)
* [Avro Schema Spec](http://avro.apache.org/docs/1.9.0/spec.html)
* [original Apache Avro Maven Plugin Git Repo (creates Java sources)](https://github.com/apache/avro/tree/master/lang/java/maven-plugin)


## How to use
```
<plugin>
    <groupId>net.stefanfuchs</groupId>
    <artifactId>avro-kotlin-maven-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>schema</goal>
            </goals>
            <configuration>
                <sourcePath>${project.basedir}/src/main/resources/avro/</sourcePath>
                <destinationPath>${project.basedir}/src/main/generated-sources/kotlin/</destinationPath>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Plugin Configuration
The following settings can be used to control the plugin

| setting             | type              | default               | description  |
|-------------------- |------------------ |---------------------- |------------- |
| sourcePath          |Path               |                       | This path will be searched recursively for files ending with [extensionFilter] extensions
| includeFileList     |List&lt;File&gt;   |                       | List of files to be included and considered as Avro Schema (no matter if the extension matches or if they are located in another directory than [sourcePath] 
| extensionFilter     |List&lt;String&gt; | '.avro', '.avsc'      | Only consider files with this extensions in [sourcePath] as Avro Schema files
| generateLogicalType |Boolean            | true                  | When true, the plugin will generate getter/setter of Kotlin data types that are closest to the logicalType (e.g. ZonedDateTime instead of Long for logicalType 'epoch-millis')
| destinationPath     |Path               | './generated-sources' | Output path, where the Kotlin class source code is written to

The plugin will fail:
* if one of the Schema definitions is invalid
* class (schema namespace + name) has been defined twice but with different schema structure
* a class already exists as a path name 

The plugin will not cleanup the destination folder before the code generation (use [maven-clean-plugin](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-clean-plugin/3.1.0) to achive this).  

## Datatype mapping
Data is internally stored by the Kotlin equivalent of the Avro type, but when a logical type is specified, a more specific data type is used for getter/setter to work with the DTO.

| Avro type  | logical type                                                                                         | Kotlin type                                       |
|------------|-----------------                                                                                     |---------------------------------------------------|
|null	     |                                                                                                      |Any? (Unions are the specific type but optional (?)|
|bytes	     |                                                                                                      |ByteArray                                          |
|bytes	     |[decimal](http://avro.apache.org/docs/1.9.0/spec.html#Decimal)                                        |BigDecimal                                         |
|string	     |                                                                                                      |String                                             |
|string	     |[uuid](http://avro.apache.org/docs/1.9.0/spec.html#UUID)                                              |UUID                                               |
|int         |                                                                                                      |Int                                                |
|int         |[date](http://avro.apache.org/docs/1.9.0/spec.html#Date)                                              |LocalDate                                          |
|int         |[time-millis](http://avro.apache.org/docs/1.9.0/spec.html#Time+%28millisecond+precision%29)           |LocalTime                                          |
|long	     |                                                                                                      |Long                                               |
|long	     |[time-micros](http://avro.apache.org/docs/1.9.0/spec.html#Time+%28microsecond+precision%29)           |LocalTime                                          |
|long	     |[timestamp-millis](http://avro.apache.org/docs/1.9.0/spec.html#Timestamp+%28millisecond+precision%29) |ZonedDateTime (UTC)                                |
|long	     |[timestamp-micros](http://avro.apache.org/docs/1.9.0/spec.html#Timestamp+%28microsecond+precision%29) |ZonedDateTime (UTC)                                |
|boolean	 |                                                                                                      |Boolean                                            |
|float       |                                                                                                      |Float                                              |
|double	     |                                                                                                      |Double                                             |
|record	     |                                                                                                      |org.apache.avro.specific.SpecificRecord            |
|enum	     |                                                                                                      |org.apache.avro.generic.GenericEnumSymbol          |
|array	     |                                                                                                      |Array<>                                            |
|map	     |                                                                                                      |MutableMap<>                                       |
|fixed	     |                                                                                                      |org.apache.avro.specific.SpecificFixed             |
