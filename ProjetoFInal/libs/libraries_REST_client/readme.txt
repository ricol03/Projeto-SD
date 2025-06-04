Netbeans 20 a 24:
Projetos de clientes REST

Fazer download do JAX-RS 2.1 / Jersey 2.26+ em:
https://eclipse-ee4j.github.io/jersey/download.html

ou com o link direto:
https://repo1.maven.org/maven2/org/glassfish/jersey/bundles/jaxrs-ri/2.46/jaxrs-ri-2.46.zip

Descompactar o ficheiro zip e acrescentar as seguintes libraries .JAR contidas nesta pasta (Add Jar/Folder):
- todas de jaxrs-ri-2.46\jaxrs-ri\api
- todas de jaxrs-ri-2.46\jaxrs-ri\ext
- todas de jaxrs-ri-2.46\jaxrs-ri\lib

Para utilização de XML, acrescentar a seguinte library já presentes na IDE (Add Library):
- JAXB 2.3.5

Observação:
As anotações JAXB nas classes cujos objetos serão convertidos de/para XML e JSON são de packages diferentes:

Servidor:
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

Cliente:
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

Francisco Afonso
29/04/2025



