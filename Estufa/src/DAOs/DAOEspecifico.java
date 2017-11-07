package DAOs;

import Entidade.Sensor;
import daos.DAOGenerico;
import static daos.DAOGenerico.em;
import java.util.ArrayList;
import java.util.List;

public class DAOEspecifico extends DAOGenerico<Sensor> {

    public DAOEspecifico() {
        super(Sensor.class);
    }

    public int autoIdSensor() {
        Integer a = (Integer) em.createQuery("SELECT MAX(e.idSensor) FROM Sensor e ").getSingleResult();
        if (a != null) {
            return a + 1;
        } else {
            return 1;
        }
    }

    public List<Sensor> listByNome(String nome) {
        return em.createQuery("SELECT e FROM Sensor e WHERE e.nomeSensor LIKE :nome").setParameter("nome", "%" + nome + "%").getResultList();
    }

    public List<Sensor> listById(int id) {
        return em.createQuery("SELECT e FROM Sensor e WHERE e.idSensor = :id").setParameter("id", id).getResultList();
    }

    public List<Sensor> listInOrderNome() {
        return em.createQuery("SELECT e FROM Sensor e ORDER BY e.nomeSensor").getResultList();
    }

    public List<Sensor> listInOrderId() {
        return em.createQuery("SELECT e FROM Sensor e ORDER BY e.idSensor").getResultList();
    }

    public List<String> listInOrderNomeStrings(String qualOrdem) {
        List<Sensor> lf;
        if (qualOrdem.equals("id")) {
            lf = listInOrderId();
        } else {
            lf = listInOrderNome();
        }

        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lf.size(); i++) {
            ls.add(lf.get(i).getIdSensor() + "-" + lf.get(i).getNomeSensor());
        }
        return ls;
    }
}
