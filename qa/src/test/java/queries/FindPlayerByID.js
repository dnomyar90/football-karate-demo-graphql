function () {
    var query =
        'query FindPlayerByID($id: Int!) {\
            FindPlayerByID(id: $id){\
                    ...Player\
                }\
                }';

    var fragments = [
        karate.call('classpath:fragments/FragmentPlayer.js').Player()
    ];
    return karate.read('classpath:helper.js')(query, fragments);
}