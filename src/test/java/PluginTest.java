
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.mockbukkit.mockbukkit.MockBukkit;

import dev.hyperlisk.chatmaster.ChatMaster;

public class PluginTest {

    private static ChatMaster plugin;

    @BeforeAll
    public static void setUp() {
        MockBukkit.mock();


        plugin = MockBukkit.load(ChatMaster.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    public void testPlugin() {

        
    }
}
