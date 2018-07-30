package service;

/**阅读动作
 * @author haoman
 */
public interface Books {
    /**查找书的资源
     * @param bookName
     */
    void findBooks(String bookName);

    /**
     * 查看书的内容
     * @param bookCount
     */
    void readBooks(Integer bookCount);
}
