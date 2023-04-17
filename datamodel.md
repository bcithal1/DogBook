[Full Table View](https://mermaid.live/edit#pako:eNq1V91T4joU_1c6fVZHVBR5Y9aqHVngUtydueNMJrShzVia3ibousj_vidpCmmbqvtwfUA453e-P5Js3ZBFxB26DvyR4obiuMDrp0z-fAy8ufP-fnz8_q6-o8BbLPzJXeAMnQTzp6wBY1vndu57kxs09_559IIF4ChHIiGoIP9tCBes-LoIjqKCcE5Il0hw788AznMS0hUlvMQZvF3pestAwtKo7T0gZ6M7D91Ox-PpT28eSE_athVmdPPdn4AmPygBkmhXAqD7UQvVUKMQJeZuOhqjYDFaPAYVVJIAFKaYcyNQC_Lb_Wg89iZgpQ0_8HZsX9HuQno_vMlCc5U1mTs09iGBOk6FcAyIIiB_8sNfeGbbSGdwhjjJom6gys1XgIbGgoSEvpAvKf0UawQ4dDZ5hAUx46pFXYl2Zm82VWoiVunYl97GHPsP3g2S5KBRHOl5Sp9JJ07rAjcUbu_MzfQOTX9ODq0NBMCx14y32_4APrS8xFsapWLpUKRkOV4NAGoMnVZVelHWQy2EvxEwCmi4sJj73x4A85yxV8P-bD699cdePXxdrafMQGzl7538UJq2pYpss16SwhEFDZ8RjZz8uUaPWCypqzqVP9M0LSkcJLPYyfCaaO1G8eo2csaFRdmGk6IiKwVS1C7ZcA5vRMIKi8qQrdckkyI18gslryglLyStkWU_oZBtMmFVYnB0sGvY1jjWFZLzoz6QoPscPGXm-NSDSexpiGWXWOjgbiZq-fkfVR92w7Yb2VE7szBhCCkyU60zFzP4RFxgseHaZHsk6qb1_IAdey_uT0-T39Bc14jTPMEd2tiaxLilqX7SNXoTOuHzrj6cgn8t3ZlXrdausBoVnfiJmk-DgJdsAxXNVszIVrkbt1ZXbh9si6FBbRd-yVgqtzHRFyKwJdes3cbsoebjihbQt1nTdTjvLVSyxrTebLozarQ8YRmZKKv6eJiPnbxgK5oSBEzBmlPNVmhJC5HU-xhObiOi6pzZ3xt1eOaCZALuKCEWlFUn0wdJNgQV6xBtk5PD5eeVFVGLm7LSGNwyhABCzVm1QrYtN_ZjPnto8ardYnGxFBNUpKSDVznTwY4oDw-s5kY1_LYuitqyUGdnK5fVuaXK1mQqe2FB4F-EPrIr79rbLyr9xCFlc1VQkOUJzVFpXllv5ciAibfc4NdchPlt-6andNaumbWjlgUh7U7i9HcbysmvA00NOU5hSZvi2odXQuNE7N01t0V80FuLxbjxW_KtTg8kX3MfRLVrPAbaeuxtHiY4TUkWf1RRmlM9J7efJVbVGTwuRKO2igH0mFg5cPnIU1I15Oc52LlH7poUsAMjeOKqYJ9ceFyCL-4Qvi7TDXyDVQ84uDax4C0L3eEKp5wcueUjQL-IG1QvovCYdYei2AAtx5k73Lq_3OHxef_05Pz6_HxwNbjs9U-ve0fuG5DPrvon572r04vB9eV173pw1t8dub8ZA729k_7pWf-s1-tfXAwuzwanV0cuUeq_ly9z9UBXRv5VAtLm7g9noPel)

```mermaid
    erDiagram
    USER ||--|| USER_SETTINGS : has

    USER ||--o{ FRIEND_REQUEST : is_the_requestor
    USER ||--o{ FRIEND_REQUEST : is_the_addressee
    USER ||--o{ FRIENDSHIP : specifies
    FRIENDSHIP }|--|| FRIEND_REQUEST : holds

    USER ||--|| PAGE_FOLLOWERS: is
    USER ||--o{ PAGE_ADMIN : IS
    PAGE ||--|| PAGE_FOLLOWERS : HAS
    PAGE ||--|{ PAGE_ADMIN : HAS

    GOAL_STATUS ||--|{ GOAL : classifies
    GOAL_STATUS ||--|{ CHALLENGE : classifies
    CHALLENGE }o--|| USER : has

    USER ||--o{ EVENT: has
    GUEST_LIST ||--|| EVENT : has
    EVENT_INVITE ||--|| USER : can_send
    EVENT_INVITE ||--|| PAGE : can_send
    EVENT_INVITE ||--|| USER : can_receive
    EVENT_INVITE ||--|| PAGE : can_receive
    EVENT_INVITE ||--|| GUEST_LIST : updates
    EVENT ||--|| EVENT_INVITE : has

    USER ||--o{ POST : does
    PAGE ||--o{ POST : does
    LIKED_POSTS }o--|| USER : can_like
    LIKED_POSTS }o--|| POST : has_likes

    DOG_OWNER ||--|| DOG : owns
    USER ||--|| DOG_OWNER : is
    DOG }o--|| USER : has
    DOG ||--o{ DOG_FRIEND : has
    DOG_FRIEND_REQUEST }o--|| DOG : can_request
    DOG_FRIEND_REQUEST }o--|| DOG : can_receive
    DOG ||--o{ TRICK : knows
    DOG_PROFILE ||--|| DOG : has


DOG_PROFILE{

}

TRICK{
    number trick_id pk
    number dog_id fk
    number skill
    string trickName
}

LIKED_POSTS{
    number post_id fk
    number user_id fk
}

POST{
    number post_id pk
    number author_id fk
    number comment_id
    number view_level
    string message
    date date_time
}


EVENT_INVITE{
    number host_id fk
    number guest_id fk
    number event_id fk
}

GUEST_LIST{
    number event_id fk
    number user_id fk
    number access_level
    string going_status
}

DOG_FRIEND_REQUEST{
    number requester_dog_id fk
    number addressee_dog_id fk
}

DOG_FRIEND{
    number alpha_dog_id fk
    number omega_dog_id fk
}

PAGE_FOLLOWERS{
    number page_id fk
    number user_id fk
}
PAGE_ADMIN{
    number page_id fk
    number user_id fk
    number access_level
}
PAGE{
    number page_id pk
    string Name
    string about_info
}

DOG_OWNER {
    number user_id FK
    number dog_id FK
    number access_level
    bool owner
    }
USER{
    number user_id PK
    string first_name
    string last_name
    string email
    string phoneNumber
    URL profile_photo
    date date_of_birth
    string gender
    }
    USER_SETTINGS{
        string notifications
        number user_id FK
        string user_name
        string user_password
        string location_setting
    }
    EVENT{
        number event_id PK
        number host_id FK
        string event_title
        string event_location
        string event_disc
        date date_time
    }
    FRIEND_REQUEST{
        number receiver_id FK
        number sender_id FK
        date created_date_time
    }
    FRIENDSHIP{
        number sender_id FK
        number receiver_id FK
        date friendship_create_date
        string friendship_type
        
    }
    DOG{
        number dog_id PK
        string name
        string breed
        string breed_id
        string size
        string sex
        bool altered
        number weight   
        number age
       
    }
    GOAL_STATUS{
        number status_code
        string name
    }
    CHALLENGE{
        number id PK
        number challenger_id FK
        number recipient_id FK
        string name
        date start_date
        date target_date
        date completed_date
        number status_code
    }
```
