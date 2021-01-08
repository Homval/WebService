package dbService.dao;

import dbService.userDataSets.UserDataSet;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDao {
    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public UserDataSet getUserById(long id) {
        return session.get(UserDataSet.class, id);
    }

    public long addNewUser(String name, String password) {
        return (Long) session.save(new UserDataSet(name, password));
    }

    public void deleteUser(String name, String password) {
        session.delete(new UserDataSet(name, password));
    }

    public UserDataSet getUserIdByName(String name) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSet> criteria = criteriaBuilder.createQuery(UserDataSet.class);
        Root<UserDataSet> userDataSetRoot = criteria.from(UserDataSet.class);
        criteria.select(userDataSetRoot);
        criteria.where(criteriaBuilder.equal(userDataSetRoot.get("name"), name));
        Query query = session.createQuery(criteria);
        return (UserDataSet) query.getSingleResult();
    }
}
