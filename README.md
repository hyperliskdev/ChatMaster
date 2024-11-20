<div align="center">
    <img src="./assets/logo.png" alt="project logo"/> <br/>
    A Minecraft plugin to make chatting extra easy.<br/> <br/>
</div>




if the player is whispering, the target will be a UUID of some online player and the whisper boolean will be true.



## TODO

- [ ] Add channel functionality instead of this groups mess
- [ ] Add a way to see who is in your channel
- [ ] Add a way to see what channels you are in


## Examples

```json
{
  "uuid": "e0a3a51c-8e8d-4b0e-9b7c-0f1f5c9c0a9e",
  "name": "hyperliskdev",
  "channels": {
    "listening": ["global", "staff", "faction" "party"],
    "current": "global",
    },
  "whisper": {
    "enabled": true,
    "target_uuid": "e0a3a51c-8e8d-4b0e-9b7c-0f1f5c9c0a9e",
    "last_whisper": "2019-01-01T00:00:00.000Z",
  }
}
```