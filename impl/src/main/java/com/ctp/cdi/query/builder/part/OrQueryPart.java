package com.ctp.cdi.query.builder.part;

import com.ctp.cdi.query.builder.QueryBuilderContext;
import static com.ctp.cdi.query.util.QueryUtils.splitByKeyword;

/**
 *
 * @author thomashug
 */
class OrQueryPart extends ConnectingQueryPart {

    @Override
    protected QueryPart build(String queryPart) {
        String[] andParts = splitByKeyword(queryPart, "And");
        boolean first = true;
        for (String and : andParts) {
            AndQueryPart andPart = new AndQueryPart();
            andPart.setIsFirst(first);
            first = false;
            children.add(andPart.build(and));
        }
        return this;
    }

    @Override
    protected QueryPart buildQuery(QueryBuilderContext ctx) {
        if (!isFirst) {
            ctx.append(" or ");
        }
        buildQueryForChildren(ctx);
        return this;
    }
    
}
