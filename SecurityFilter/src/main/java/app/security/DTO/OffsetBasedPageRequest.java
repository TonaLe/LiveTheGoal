package app.security.DTO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * The type Offset based page request.
 */
public class OffsetBasedPageRequest implements Pageable, Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -25822477129613575L;

    /**
     * The Limit.
     */
    private int limit;
    /**
     * The Offset.
     */
    private int offset;
    /**
     * The Sort.
     */
    private final Sort sort;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param limit  the size of the elements to be returned.
     * @param offset zero-based offset.
     * @param sort   can be {@literal null}.
     */
    public OffsetBasedPageRequest(final int limit, final int offset, final Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    public OffsetBasedPageRequest(int offset, int limit) {
        this(limit, offset, Sort.unsorted());
    }


    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(getPageSize(), ((Long)getOffset()).intValue() + getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return new OffsetBasedPageRequest(getPageSize(), ((Long)getOffset()).intValue() - getPageSize(), getSort());
    }

    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(getPageSize(),0, getSort());
    }

    @Override
    public Pageable withPage(final int pageNumber) {
        return new OffsetBasedPageRequest(getPageSize(),pageNumber, getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }


}
