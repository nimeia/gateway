package gateway.worker.init;

/**
 * 系统启动数据加载器
 * 按不同的设计可以从不同的存储中加载数据，例 mysql，redis 等
 */
public interface SystemStartUpConfigDataInitialize {

    public void doInitJob();
}
