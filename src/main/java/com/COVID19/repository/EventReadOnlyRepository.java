package com.COVID19.repository;

import com.COVID19.domain.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

// org.springframework.data.repository.CrudRepository & PagingAndSortingRepository & JpaRepository Interface 참고하여 작성하면 편리
@NoRepositoryBean
public interface EventReadOnlyRepository<Event, Long> extends Repository<Event, Long> {
    Optional<Event> findById(Long id);
    List<Event> findAll();
    List<Event> findAllById(Iterable<Long> ids);
    List<Event> findAll(Sort sort);
    Page<Event> findAll(Pageable pageable);
}
