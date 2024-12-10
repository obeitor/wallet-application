package com.nimisitech.wallet.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WalletIntegrationTestApp.class})
@ActiveProfiles(value = "test")
@Slf4j
public abstract class BaseIntegrationTest {


}
