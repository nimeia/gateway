package gateway.model.repos;

import gateway.model.entity.GatewaySystem;
import gateway.model.entity.GatewaySystemApi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewaySystemApiRepos extends JpaRepository<GatewaySystemApi,Long> {
}
