# Web Services SOAP

Dans cette activité, nous allons créer un service SOAP qui permettera d'envoyer les informationd e la classe `BanqueService`.

Dans notre projet, on ajoute la dépendance suivante :

```xml
<!-- https://mvnrepository.com/artifact/com.sun.xml.ws/jaxws-ri -->
<dependency>
    <groupId>com.sun.xml.ws</groupId>
    <artifactId>jaxws-ri</artifactId>
    <version>4.0.1</version>
    <type>pom</type>
</dependency>
```

## Classe `Compte`
La classe `Compte` est la classe qui représente un compte bancaire. Elle contient les attributs suivants :

* `numero` : le numéro du compte
* `solde` : le solde du compte
* `dateCréation` : la date d'ouverture du compte

### JaxB
La classe JaxWS utilise JaxB pour la sérialisation et la désérialisation des objets. Pour cela, il faut ajouter les annotations suivantes :

* `@XmlRootElement` : pour indiquer que la classe est un élément XML
* `@XmlAccessorType` : pour indiquer que les attributs sont des éléments XML
* `@XmlTransient` : pour indiquer que l'attribut ne doit pas être sérialisé

```java
@XmlRootElement(name = "compte")
@XmlAccessorType(XmlAccessType.FIELD)
public class Compte {
    private int code;
    private double solde;
    @XmlTransient
    private Date dateCreation;
    // ...  
}
```

## Classe `BanqueService`
La classe `BanqueService` est la classe qui représente un service SOAP. Elle contient les méthodes suivantes :

* `conversion` : qui permet de convertir une somme d'euros en dirhams
* `getCompte(int code)` : qui permet de créer un compte à partir de son code
* `listComptes()` : qui permet de lister tous les comptes

### JaxWS
La classe JaxWS utilise JaxWS pour la création du service SOAP. Pour cela, il faut ajouter les annotations suivantes :

* `@WebService` : pour indiquer que la classe est un service SOAP
* `@WebMethod` : pour indiquer que la méthode est une méthode du service SOAP
* `@WebParam` : pour indiquer que le paramètre est un paramètre de la méthode du service SOAP

```java
@WebService(serviceName = "BanqueWS")
public class BanqueService {
    @WebMethod(operationName = "ConversionEuroToDH")
    public double conversionEuroToDh(@WebParam(name = "montant") double montant) {
        return montant * 10.5;
    }
    @WebMethod
    public Compte getCompte(int code){
        return new Compte(code, Math.random()*65000, new Date(0));
    }

    @WebMethod
    public List<Compte> listComptes(){
        return List.of(
                new Compte(1, Math.random()*65000, new Date(0)),
                new Compte(2, Math.random()*65000, new Date(0)),
                new Compte(3, Math.random()*65000, new Date(0))
        );

    }
}
```

## Classe `ServeurWS`
La classe `ServeurWS` est la classe qui permet de publier le service SOAP `BanqueService`. 

On utilise la méthode `publish` de la classe `Endpoint` pour publier le service SOAP. Cette méthode prend en paramètre l'URL du service et l'instance du service.

```java
public class ServeurWS {
    public static void main(String[] args) {
        String url = "http://0.0.0.0:8080/";
        Endpoint.publish(url, new BanqueService());
        System.out.println("Web service published at " + url);
    }
}
```

Maintenant que le serveur est prêt, on peut tester le service SOAP avec le client SOAP.

Nous allons initialiser un nouveau projet Maven avec le nom `clientWS`.

## Classe `ClientWS`
La classe `ClientWS` est la classe qui permet de consommer le service SOAP `BanqueService`.

On utilise la méthode `create` de la classe `Service` pour créer une instance du service SOAP. Cette méthode prend en paramètre l'URL du service et la classe du service.

```java
public class ClientWS {
    public static void main(String[] args) {
        String url = "http://localhost:8080/BanqueWS?wsdl";
        Service service = Service.create(URI.create(url).toURL(), new QName("http://ws/", "BanqueWS"));
        BanqueService banqueService = service.getPort(BanqueService.class);
        System.out.println(banqueService.conversionEuroToDh(100));
        System.out.println(banqueService.getCompte(1));
        System.out.println(banqueService.listComptes());
    }
}
```

On génère le package proxy pour tester le service SOAP. Pour cela, on utilise l'extension IntelliJ `JaxWS`.

## Exécution
Pour exécuter le serveur, on utilise la commande suivante :

```bash
mvn exec:java -Dexec.mainClass="ServeurWS"
```

Pour exécuter le client, on utilise la commande suivante :

```bash
mvn exec:java -Dexec.mainClass="ClientWS"
```

## Résultat
Le résultat de l'exécution du client est le suivant :

```bash
1050.0
Compte{code=1, solde=0.0, dateCreation=null}
[Compte{code=1, solde=0.0, dateCreation=null}, Compte{code=2, solde=0.0, dateCreation=null}, Compte{code=3, solde=0.0, dateCreation=null}]
```

## Client PHP
Pour tester le service SOAP avec un client PHP, on crée le fichier [Client.php](./ClientSOAP/Client.php).

```php
<?php
// Initialize WS with the WSDL
$client = new SoapClient("http://localhost:8080/?wsdl");
$para=new stdClass();
$para->montant=100;
$response1=$client->__soapCall("ConversionEuroToDH",array($para));
$response2=$client->__soapCall("getCompte", array(1));
$response3 = $client->__soapCall("listComptes", array());

//Convert stdClass to array
$response2=$response2->return;
$response3=$response3->return;
?>
```










"# Web_services_SOAP" 
