package DAOs;

import Entidade.Leitura;
import daos.DAOGenerico;
import static daos.DAOGenerico.em;
import java.util.ArrayList;
import java.util.List;

public class DAOLeitura extends DAOGenerico<Leitura> {

    public DAOLeitura() {
        super(Leitura.class);
    }

    public int autoIdLeitura() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idLeitura) FROM Leitura e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Leitura> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Leitura e WHERE e.nomeLeitura LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Leitura> listById(int id) {
        return em.createQuery("SELECT e FROM Leitura e WHERE e.idLeitura = :id").setParameter("id", id).getResultList();
    }

    public List<Leitura> listInOrderNome() {
        return em.createQuery("SELECT e FROM Leitura e ORDER BY e.nomeLeitura").getResultList();
    }

    public List<Leitura> listInOrderId() {
        return em.createQuery("SELECT e FROM Leitura e ORDER BY e.idLeitura").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Leitura> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getDataLeitura()+ "-" + lf.get(i).getSensorIdSensor()+ "-"+ lf.get(i).getValor());
        }
        return ls;
    }
}
