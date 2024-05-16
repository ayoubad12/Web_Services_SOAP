import proxy.BanqueService;
import proxy.BanqueWS;
import proxy.Compte;

import java.util.List;

public class ClientWS {
    public static void main(String[] args) {

        BanqueService stub = new BanqueWS().getBanqueServicePort();
        System.out.println(stub.conversionEuroToDH(100));
        Compte compte= stub.getCompte(2);
        System.out.println(compte.getCode());
        System.out.println(compte.getSolde());

        List<Compte> comptes = stub.listComptes();
        comptes.forEach(c->{
            System.out.println("-------------------");
            System.out.println(c.getCode());
            System.out.println(c.getSolde());
        });
    }
}

