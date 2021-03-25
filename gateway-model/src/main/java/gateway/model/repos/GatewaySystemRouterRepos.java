package gateway.model.repos;

import gateway.model.entity.GatewaySystem;
import gateway.model.entity.GatewaySystemRouter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewaySystemRouterRepos extends JpaRepository<GatewaySystemRouter,Long> {
}
