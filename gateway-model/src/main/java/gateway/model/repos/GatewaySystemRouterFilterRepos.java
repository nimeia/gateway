package gateway.model.repos;

import gateway.model.entity.GatewaySystemRouter;
import gateway.model.entity.GatewaySystemRouterFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewaySystemRouterFilterRepos extends JpaRepository<GatewaySystemRouterFilter,Long> {
}
