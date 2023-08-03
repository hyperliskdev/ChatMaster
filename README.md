# ChatMaster

/whisper \
/group \
/leave 

When a player joins the server a document gets created in the database,  it looks like this

```json
{
    "_id": {
        "$oid": "5f9b5b3b9c6b9b0b3c3b3b3b"
    },
    "uuid": "e0a3a51c-8e8d-4b0e-9b7c-0f1f5c9c0a9e",
    "username": "playername",
    "target": "default",
    "whisper": false
}
```

if the player is whispering, the target will be a uuid of some online player and the whisper boolean will be true.


### whisper - one on one chat
This command sets the target of your next messages to a single player. It toggles a boolean
in mongodb and the next messages will be sent to the target player.

### group - multiple player chat
A group can be any string and anyone can join it. The group is saved in mongodb and the next messages will be sent to all players in the group.

### leave - reset to regular chat
This command just resets the group to the default values and the chat will function as regular minecraft chat.



