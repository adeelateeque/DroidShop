package com.droidshop.api.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unchecked")

public abstract class AbstractDAO<T>
{
	@Autowired
	protected SessionFactory sessionFactory;

	private Class<T> entityClass;

	public AbstractDAO(Class<T> entityClass)
	{
		this.entityClass = entityClass;
	}
	
	protected Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	public Serializable save(T entity)
	{
		return sessionFactory.getCurrentSession().save(entity);
	}
	
	public void saveOrUpdate(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

	public void delete(T entity)
	{
		sessionFactory.getCurrentSession().delete(entity);
	}

	public void update(T entity)
	{
		sessionFactory.getCurrentSession().update(entity);
	}
	
	public T merge(T entity)
	{
		return (T) sessionFactory.getCurrentSession().merge(entity);
	}
	
	public T find(Serializable entityID)
	{
		return (T) sessionFactory.getCurrentSession().get(entityClass, entityID);
	}

	public T findReferenceOnly(Serializable entityID)
	{
		// load method returns a reference while get method returns the whole object
		return (T) sessionFactory.getCurrentSession().load(entityClass, entityID);
	}

	public int getCollectionSize(T entity, String collectionName)
	{
		String query = "SELECT size(p." + collectionName + ") FROM " + entityClass.getSimpleName() + " p WHERE p."
				+ getIdentityColumn() + " = :value";

		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("value", getMetaData().getIdentifier(entity, (SessionImplementor) sessionFactory.getCurrentSession()));
		return (Integer) qry.uniqueResult();
	}

	public T findOneResult(String namedQuery, Map<String, Object> parameters)
	{
		T result = null;

		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

			populateQueryParameters(query, parameters);

			result = (T) query.uniqueResult();

		}
		catch (NonUniqueResultException e)
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

			populateQueryParameters(query, parameters);

			result = (T) query.list().get(0);
		}
		catch (NoResultException e)
		{
			System.out.println("No result found for named query: " + namedQuery);
		}
		catch (Exception e)
		{
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	public List<T> findResults(String namedQuery, Map<String, Object> parameters)
	{
		List<T> result = null;

		try
		{
			Query query = sessionFactory.getCurrentSession().getNamedQuery(namedQuery);

			populateQueryParameters(query, parameters);

			result = (List<T>) query.list();

		}
		catch (NoResultException e)
		{
			System.out.println("No result found for named query: " + namedQuery);
		}
		catch (Exception e)
		{
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters)
	{
		if (parameters != null && !parameters.isEmpty())
		{
			for (Entry<String, Object> entry : parameters.entrySet())
			{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	protected ClassMetadata getMetaData()
	{
		return sessionFactory.getClassMetadata(entityClass);
	}

	protected Class<?> getColumnType(String column)
	{
		return getColumnHibernateType(column).getReturnedClass();
	}

	protected Type getColumnHibernateType(String column)
	{
		return getMetaData().getPropertyType(column);
	}

	protected String getIdentityColumn()
	{
		return getMetaData().getIdentifierPropertyName();
	}

	public List<T> getAll()
	{
		return (List<T>) sessionFactory.getCurrentSession().createQuery("from " + entityClass.getSimpleName()).list();
	}

	public List<T> getAllOrderBy(String orderby, String order, Boolean distinct)
	{
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		String query = "";
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p ORDER BY p." + orderby + " " + order;
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p ORDER BY p." + orderby + " " + order;
		}
	
		return (List<T>) sessionFactory.getCurrentSession().createQuery(query).list();
	}

	public List<T> getAllWhereLessThan(String column, Object lessThan, Boolean inclusive, Boolean distinct, String orderby,
			String order)
	{
		lessThan = getColumnType(column).cast(lessThan);
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		if (lessThan.getClass().getSimpleName().equals("String"))
		{
			lessThan = "'" + lessThan + "'";
		}
	
		String query = "";
	
		if (distinct && inclusive)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " <= :lessThan";
		}
		else if (distinct && !inclusive)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " < :lessThan";
		}
		else if (!distinct && inclusive)
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " <= :lessThan";
		}
		else if (!distinct && !inclusive)
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " < :lessThan";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("lessThan", lessThan, getColumnHibernateType(column));
	
		return (List<T>) qry.list();
	}

	public List<T> getAllWhereGreaterThan(String column, Object greaterThan, Boolean inclusive, Boolean distinct,
			String orderby, String order)
	{
		greaterThan = getColumnType(column).cast(greaterThan);
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		if (greaterThan.getClass().getSimpleName().equals("String"))
		{
			greaterThan = "'" + greaterThan + "'";
		}
	
		String query = "";
	
		if (distinct && inclusive)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " >= :greaterThan";
		}
		else if (distinct && !inclusive)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " > :greaterThan";
		}
		else if (!distinct && inclusive)
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " >= :greaterThan";
		}
		else if (!distinct && !inclusive)
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " > :greaterThan";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("greaterThan", greaterThan, getColumnHibernateType(column));
	
		return (List<T>) qry.list();
	}

	public List<T> getAllWhereEqualsTo(String column, Object equalsTo, Boolean negate, Boolean distinct, String orderby,
			String order)
	{
		equalsTo = getColumnType(column).cast(equalsTo);
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		if (equalsTo.getClass().getSimpleName().equals("String"))
		{
			equalsTo = "'" + equalsTo + "'";
		}
	
		String query = "";
	
		String equalToOrNot = "=";
	
		if (negate)
		{
			equalToOrNot = "<>";
		}
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + equalToOrNot
					+ " :equalsTo";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + equalToOrNot + " :equalsTo";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("equalsTo", equalsTo, getColumnHibernateType(column));
	
		return (List<T>) qry.list();
	}

	/*
	 * public List<T> getAllWhereEqualsTo(String column, List<Object> equalsToElements, String
	 * anyOrSome, Boolean negate,
	 * Boolean distinct, String orderby, String order)
	 * {
	 * }
	 */
	
	public List<T> getAllWhereLike(List<String> columns, String like, String andOr, Boolean distinct, String orderby,
			String order)
	{
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		if (andOr == null || !andOr.equalsIgnoreCase("AND") || !andOr.equalsIgnoreCase("OR") || andOr.trim().equals(""))
		{
			andOr = "OR";
		}
	
		andOr = andOr.toUpperCase();
	
		if (like.getClass().getSimpleName().equals("String"))
		{
			like = "'" + like + "'";
		}
	
		String query = "";
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE ";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE ";
		}
	
		for (int i = 0; i < columns.size(); i++)
		{
			String column = columns.get(i);
	
			if (i == 0)
			{
				query += "p." + column + " LIKE " + like;
			}
			else
			{
				query += " " + andOr + " p." + column + " LIKE " + like;
			}
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		return (List<T>) sessionFactory.getCurrentSession().createQuery(query).list();
	}

	public List<T> getAllWhereLike(String column, String like, Boolean distinct, String orderby, String order)
	{
		ArrayList<String> columns = new ArrayList<String>();
		columns.add(column);
		return getAllWhereLike(columns, like, "OR", distinct, orderby, order);
	}

	public List<T> getAllWhereIn(String column, List<?> items, Boolean negate, Boolean distinct, String orderby, String order)
	{
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		String query = "";
	
		String inOrNot = "IN";
	
		if (negate)
		{
			inOrNot = "NOT IN";
		}
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + inOrNot
					+ " (:items)";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + inOrNot + " (:items)";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameterList("items", items, getColumnHibernateType(column));
	
		return (List<T>) qry.list();
	}

	public List<T> getAllWhereInCollection(String collectionColumn, Object item, Boolean negate, Boolean distinct,
			String orderby, String order)
	{
		item = getColumnType(collectionColumn).cast(item);
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		String query = "";
	
		String inOrNot = "IN";
	
		if (negate)
		{
			inOrNot = "NOT IN";
		}
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE (:item) " + inOrNot + " ELEMENTS(p."
					+ collectionColumn + ")";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE (:item) " + inOrNot + " ELEMENTS(p."
					+ collectionColumn + ")";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("item", item);
	
		System.out.println(qry.toString());
	
		return (List<T>) qry.list();
	}

	public List<T> getAllWhereInCollection(String collectionColumn, List<?> items, Boolean negate, Boolean distinct,
			String orderby, String order)
	{
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		String query = "";
	
		String inOrNot = "IN";
	
		if (negate)
		{
			inOrNot = "NOT IN";
		}
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE (:items) " + inOrNot + " ELEMENTS(p."
					+ collectionColumn + ")";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE (:items) " + inOrNot + " ELEMENTS(p."
					+ collectionColumn + ")";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("items", items);
	
		System.out.println(qry.toString());
	
		return (List<T>) qry.list();
	}

	public List<T> getAllWhereBetween(String column, Object start, Object end, Boolean negate, Boolean distinct,
			String orderby, String order)
	{
		start = getColumnType(column).cast(start);
		end = getColumnType(column).cast(end);
	
		if (order == null || !order.equalsIgnoreCase("ASC") || !order.equalsIgnoreCase("DESC") || order.trim().equals(""))
		{
			order = "ASC";
		}
	
		if (start.getClass().getSimpleName().equals("String"))
		{
			start = "'" + start + "'";
		}
	
		if (end.getClass().getSimpleName().equals("String"))
		{
			end = "'" + end + "'";
		}
	
		String query = "";
	
		String betweenOrNot = "BETWEEN";
	
		if (negate)
		{
			betweenOrNot = "NOT BETWEEN";
		}
	
		if (distinct)
		{
			query = "SELECT DISTINCT p FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + betweenOrNot
					+ " :start AND :end";
		}
		else
		{
			query = "FROM " + entityClass.getSimpleName() + " p WHERE p." + column + " " + betweenOrNot + " :start AND :end";
		}
	
		if (orderby != null && !orderby.trim().equals(""))
		{
			query += " ORDER BY p." + orderby + " " + order;
		}
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("start", start, getColumnHibernateType(column));
		qry.setParameter("end", end, getColumnHibernateType(column));
	
		return (List<T>) qry.list();
	}

	public T loadWithCollection(T entity, String collectionToLoad)
	{
		String query = "FROM " + entityClass.getSimpleName() + " p LEFT JOIN FETCH p." + collectionToLoad + " WHERE p."
				+ getIdentityColumn() + " = :value";
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("value", getMetaData().getIdentifier(entity, (SessionImplementor) sessionFactory.getCurrentSession()));
	
		if (qry.uniqueResult() == null)
		{
			return entity;
		}
		return (T) qry.uniqueResult();
	}

	public T loadWithCollection(T entity, List<String> collectionsToLoad)
	{
		String query = "FROM " + entityClass.getSimpleName() + " p ";
		for (String collection : collectionsToLoad)
		{
			query += " LEFT JOIN FETCH p." + collection;
		}
		query += " WHERE p." + getIdentityColumn() + " = :value";
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("value", getMetaData().getIdentifier(entity, (SessionImplementor) sessionFactory.getCurrentSession()));
	
		if (qry.uniqueResult() == null)
		{
			return entity;
		}
		return (T) qry.uniqueResult();
	}

	public T loadWithCollection(Serializable id, String collectionToLoad)
	{
		String query = "FROM " + entityClass.getSimpleName() + " p LEFT JOIN FETCH p." + collectionToLoad + " WHERE p."
				+ getIdentityColumn() + " = :value";
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("value", id);
		return (T) qry.uniqueResult();
	}

	public T loadWithCollection(Serializable id, List<String> collectionsToLoad)
	{
		String query = "FROM " + entityClass.getSimpleName() + " p ";
		for (String collection : collectionsToLoad)
		{
			query += " LEFT JOIN FETCH p." + collection;
		}
		query += " WHERE p." + getIdentityColumn() + " = :value";
	
		Query qry = sessionFactory.getCurrentSession().createQuery(query);
		qry.setParameter("value", id);
		return (T) qry.uniqueResult();
	}
}