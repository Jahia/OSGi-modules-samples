# OSGi-modules-samples

This repository contains samples of modules to create Jahia Actions, Filters, etc... with OSGi

- [Action samples, contains samples for Jahia Action](./action-samples)
- [Filter samples, contains samples for Jahia Filter](./filter-samples)
- [Background Job samples, contains samples for Background Job](./background-job-samples)
- [ChoiceList samples, contains samples for Jahia ChoiceList Initializer and Renderer](./choicelist-samples)
- [Service samples, contains samples to create and use OSGi services between modules](./service-samples)
- [Datasource samples, contains samples for Jahia Datasource](./datasource-samples)
- [Interceptor samples, contains samples for Jahia Interceptor](./interceptor-samples)
- [Cache Key Part Generator samples, contains samples for Jahia Cache Key Part Generator](./interceptor-samples)

## How to use OSGi annotations in your module

In order for the OSGi annotations to work in your module, you need to make sure that you have this tag `<_dsannotations>*</_dsannotations>` in the `maven-bundle-plugin` plugin instructions of your pom.xml:

```xml
<plugin>
    <groupId>org.apache.felix</groupId>
    <artifactId>maven-bundle-plugin</artifactId>
    <extensions>true</extensions>
    <configuration>
        <instructions>
            <_dsannotations>*</_dsannotations>
        </instructions>
    </configuration>
</plugin>
```
