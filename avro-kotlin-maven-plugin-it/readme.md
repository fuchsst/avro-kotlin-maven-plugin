This project is to test the plugin and check if the generated Kotlin classes produce the same Avro data as the Apacha Avro Maven Plugin that is used as a reference implementation.

resources/avro contains a subfolder for each Kotlin and Java with the same schema, the namespace is the only difference (so there are no naming conflicts).

When compiling the module both plugins should generate the appropriate SpecificRecord Avro classes that are use to de-/serialize in both directions. 
