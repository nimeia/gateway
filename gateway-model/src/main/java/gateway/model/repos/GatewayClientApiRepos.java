package gateway.model.repos;

import gateway.model.entity.GatewayClient;
import gateway.model.entity.GatewayClientApi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatewayClientApiRepos extends JpaRepository<GatewayClientApi,Long> {
}
