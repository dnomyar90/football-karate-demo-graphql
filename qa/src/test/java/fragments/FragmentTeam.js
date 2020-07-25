function() {

    var Team =
      'fragment Team on Team {\
        id\
        name\
        is_league_winner\
        current_competitions\
        league{\
          ...League\
        }\
     }';
  
  
    return {
      Team: function () { return [Team,
        karate.call('classpath:fragments/FragmentLeague.js').League()
      ]
    }
  }
}