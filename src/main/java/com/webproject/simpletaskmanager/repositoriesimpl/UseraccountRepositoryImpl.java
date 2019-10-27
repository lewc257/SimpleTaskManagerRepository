package com.webproject.simpletaskmanager.repositoriesimpl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.webproject.simpletaskmanager.entities.Useraccount;
import com.webproject.simpletaskmanager.repositories.UseraccountRepository;

@Repository
public class UseraccountRepositoryImpl implements UseraccountRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	public UseraccountRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Useraccount findUseraccountById(Integer id) {
		return em.find(Useraccount.class, id);
	}

	@Override
	public Useraccount findUseraccountByUsername(String username) {
		String strQuery = "SELECT u FROM Useraccount u WHERE u.username = :username";
		List<Useraccount> results = em.createQuery(strQuery, Useraccount.class)
				.setParameter("username", username).getResultList();
		return results.size() == 0 ? null : results.get(0);
	}

	@Override
	public Useraccount saveAccount(Useraccount useraccount) {
		if (useraccount.getId() == null) {
			em.persist(useraccount);
		} else {
			useraccount = em.merge(useraccount);
		}
		return useraccount;
	}

	@Override
	public void deleteAccount(Useraccount useraccount) {
		if (em.contains(useraccount)) {
			em.remove(useraccount);
		} else {
			em.merge(useraccount);
		}
	}
}
