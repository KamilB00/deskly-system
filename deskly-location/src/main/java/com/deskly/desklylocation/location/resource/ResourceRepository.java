package com.deskly.desklylocation.location.resource;

import org.springframework.data.jpa.repository.JpaRepository;

interface ResourceRepository extends JpaRepository<Resource, ResourceId> {
}
