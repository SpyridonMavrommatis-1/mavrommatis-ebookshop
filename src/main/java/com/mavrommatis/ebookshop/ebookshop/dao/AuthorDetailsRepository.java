package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDetailsRepository extends JpaRepository<AuthorDetails, Integer> {

}
