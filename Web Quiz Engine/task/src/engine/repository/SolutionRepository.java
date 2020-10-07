package engine.repository;

import engine.model.Solution;
import engine.model.SolutionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Page<SolutionProjection> findByUserId(Long userId, Pageable pageable);
}
