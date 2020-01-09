package mareksebera.cz.redditswipe.dao;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class Subreddit {

    @Id
    public long id;

    @Unique
    @Index
    public String name;

    static Subreddit withName(String providedName) {
        return new Subreddit() {{
            name = providedName;
        }};
    }
}
