<!-- Plugin description -->
Plugin stores your project version from root pom.xml in `${ProjectVersion}` property.  
  
1. Open **Settings | Editor | File and Code Templates**
2. Make sure **File Header** exists in **Includes** tab
3. Edit template to include `${ProjectVersion}` property.

```java
/**
 * @since ${ProjectVersion}
 */
```
<!-- Plugin description end -->

