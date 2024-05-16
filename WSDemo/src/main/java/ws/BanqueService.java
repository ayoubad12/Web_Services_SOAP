package ws;

import java.util.Date;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

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
