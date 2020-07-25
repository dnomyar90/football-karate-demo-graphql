function(query, fragments) {
    if (fragments != null) {
        var flattened = fragments.toString().split(',');
        for (i = 0; i < flattened.length; i++) {
            if (query.indexOf(flattened[i]) === -1)
                query = query + '\n' + '\n' + flattened[i];
        }
    }
    query = query.replace(/ {2,}/g, "\n");
    return query;
}