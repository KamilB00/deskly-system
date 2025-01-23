package com.deskly.desklylocation.location;

import org.springframework.data.jpa.repository.JpaRepository;

interface LocationRepository extends JpaRepository<Location, LocationId> {


}
