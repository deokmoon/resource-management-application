package com.server.resource.management.ui;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Service
@ActiveProfiles("test")
public class DatabaseSetUp {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void execute() {
        entityManager.flush();

        entityManager.createNativeQuery("INSERT INTO server_resource(name, total_cpu, total_memory)VALUES('서버-1', 20, 100)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO server_resource (name, total_cpu, total_memory) VALUES ('서버-2', 30, 500)").executeUpdate();
    }
}
