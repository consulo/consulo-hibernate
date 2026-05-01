package com.intellij.javaee.model.common.persistence;

/**
 * Stub for com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants.
 * Constants used in JavaEE/JPA persistence.
 */
public interface JavaeePersistenceConstants {
  String JAVA_SQL_BLOB = "java.sql.Blob";
  String JAVA_SQL_CLOB = "java.sql.Clob";

  String PERSISTENCE_UNIT_ANNOTATION = "jakarta.persistence.PersistenceUnit";
  String PERSISTENCE_CONTEXT_ANNOTATION = "jakarta.persistence.PersistenceContext";
  String ENTITY_MANAGER_CLASS = "jakarta.persistence.EntityManager";
  String ENTITY_MANAGER_FACTORY_CLASS = "jakarta.persistence.EntityManagerFactory";

  // Class-level annotations
  String ENTITY_ANNO = "jakarta.persistence.Entity";
  String TABLE_ANNO = "jakarta.persistence.Table";
  String EMBEDDABLE_ANNO = "jakarta.persistence.Embeddable";
  String MAPPED_SUPERCLASS_ANNO = "jakarta.persistence.MappedSuperclass";

  // Member-level annotations
  String ID_ANNO = "jakarta.persistence.Id";
  String EMBEDDED_ID_ANNO = "jakarta.persistence.EmbeddedId";
  String VERSION_ANNO = "jakarta.persistence.Version";
  String BASIC_ANNO = "jakarta.persistence.Basic";
  String ONE_TO_ONE_ANNO = "jakarta.persistence.OneToOne";
  String ONE_TO_MANY_ANNO = "jakarta.persistence.OneToMany";
  String MANY_TO_ONE_ANNO = "jakarta.persistence.ManyToOne";
  String MANY_TO_MANY_ANNO = "jakarta.persistence.ManyToMany";
  String EMBEDDED_ANNO = "jakarta.persistence.Embedded";
  String TRANSIENT_ANNO = "jakarta.persistence.Transient";

  // Column annotations
  String COLUMN_ANNO = "jakarta.persistence.Column";
  String JOIN_COLUMN_ANNO = "jakarta.persistence.JoinColumn";
}
