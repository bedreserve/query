package com.ctp.cdi.query.meta.extractor;

import com.ctp.cdi.query.Dao;
import com.ctp.cdi.query.NonEntity;
import com.ctp.cdi.query.meta.DaoEntity;
import com.ctp.cdi.query.meta.verifier.EntityVerifier;
import com.ctp.cdi.query.meta.verifier.Verifier;
import com.ctp.cdi.query.util.EntityUtils;

public class AnnotationMetadataExtractor implements MetadataExtractor {

    private final Verifier<Class<?>> verifier;

    public AnnotationMetadataExtractor() {
        this.verifier = new EntityVerifier();
    }

    @Override
    public DaoEntity extract(Class<?> daoClass) {
        Dao dao = daoClass.getAnnotation(Dao.class);
        Class<?> daoEntity = dao.value();
        boolean isEntityClass = !NonEntity.class.equals(daoEntity) && verifier.verify(daoEntity);
        if (isEntityClass) {
            return new DaoEntity(daoEntity, EntityUtils.primaryKeyClass(daoEntity));
        }
        return null;
    }

}
