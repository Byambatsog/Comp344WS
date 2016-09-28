package com.comp344.ecommerce.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: erdenetsogt
 * Date: 12/19/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateBaseRepo<I> extends HibernateDaoSupport implements BaseRepo<I>{

    protected Class<I> domain;
    protected String domainName;

    public HibernateBaseRepo(Class<I> domain){
        this.domain = domain;
        this.domainName = domain.getSimpleName();
    }

    @Override
    @Transactional
    public void save(I item){
        getHibernateTemplate().saveOrUpdate(item);
        getHibernateTemplate().flush();
    }

    @Override
    public I get(Integer id){
        if (id==null||id.equals(0)) return null;
        return getHibernateTemplate().get(domain,id);
    }

    @Override
    public void delete(I item){
        getHibernateTemplate().delete(item);
        getHibernateTemplate().flush();
    }

    @Override
    public List<I> list(){
        return getHibernateTemplate().find("from "+domainName);
    }

    protected List getResult(final String queryString, final Object[] values, final int pageNumber, final int pageSize) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                Query queryObject = session.createQuery(queryString);

                if(getHibernateTemplate().isCacheQueries()){
                    queryObject.setCacheable(true);
                    if (getHibernateTemplate().getQueryCacheRegion() != null) {
                        queryObject.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
                    }
                }

                queryObject.setMaxResults(pageSize);
                queryObject.setFirstResult(pageSize * (pageNumber-1));

                if (values != null) {
                    for (int i = 0; i < values.length; i++) {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.list();
            }
        });
    }

    protected int getResultTotalSize(final String query,final Object[] values){
        return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query queryObject=session.createQuery(query);

            if(getHibernateTemplate().isCacheQueries()){
                queryObject.setCacheable(true);
                if (getHibernateTemplate().getQueryCacheRegion() != null) {
                    queryObject.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
                }
            }

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    queryObject.setParameter(i, values[i]);
                }
            }

            Long count = (Long) queryObject.uniqueResult();

            return count.intValue();
            }
        });
    }

    protected int getCount(final String query,final Object[] values){
        return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query queryObject=session.createQuery(query);

            if(getHibernateTemplate().isCacheQueries()){
                queryObject.setCacheable(true);
                if (getHibernateTemplate().getQueryCacheRegion() != null) {
                    queryObject.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
                }
            }

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    queryObject.setParameter(i, values[i]);
                }
            }

            Long count = (Long) queryObject.uniqueResult();

            return count.intValue();
            }
        });
    }

    protected int getSum(final String query,final Object[] values){
        return (Integer)getHibernateTemplate().execute(new HibernateCallback(){
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
            Query queryObject=session.createQuery(query);

            if(getHibernateTemplate().isCacheQueries()){
                queryObject.setCacheable(true);
                if (getHibernateTemplate().getQueryCacheRegion() != null) {
                    queryObject.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
                }
            }

            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    queryObject.setParameter(i, values[i]);
                }
            }

            List list = queryObject.list();

                if(list ==null){
                    logger.error("list is null");
                }

            //logger.error("Sum=" + list.get(0));
            if(list.size()==0){
                return 0;
            } else {
                Long count = (Long)list.get(0);
                return count.intValue();
            }
            }
        });
    }
}
