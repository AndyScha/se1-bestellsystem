package system;

/**
* Generic Repository{@code <T, ID>} to store and retrieve objects.
* Examples are business objects of classes: Customer, Order and Article.
*
* @param T entity type, T: Customer, Order, Article
* @param ID type of identifier (id)
*/
public interface Repository<T, ID> {
    /**
     * Saves object (entity) to a repository.
     *
     * @param entity to save, entity must not be null.
     * @return the saved entity, will never be null.
     */
    public T save(T entity);
}