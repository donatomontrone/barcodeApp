package org.doazz.barcode.dao;

import jakarta.persistence.TypedQuery;
import org.doazz.barcode.model.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ItemDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("barcodePU");

    public Item findByBarcode(String barcode) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Item.class, barcode);
        }
    }

    public Item findByName(String name) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT i FROM item i WHERE i.name = :name";
            TypedQuery<Item> query = em.createQuery(jpql, Item.class);
            query.setParameter("name", name);
            return query.getResultStream().findFirst().orElse(null);
        }
    }

    public List<Item> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT i from item i", Item.class).getResultList();
        }
    }

    public void save(Item item) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(Item item) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(item);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(String barcode) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Item item = em.find(Item.class, barcode);
            if (item != null) {
                em.remove(item);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
