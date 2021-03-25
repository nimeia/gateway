package gateway.model.repos;

import gateway.model.entity.GatewaySystemRouterFilter;
import gateway.model.entity.GatewaySystemRouterPredicate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewaySystemRouterPredicateRepos extends JpaRepository<GatewaySystemRouterPredicate,Long> {
}
