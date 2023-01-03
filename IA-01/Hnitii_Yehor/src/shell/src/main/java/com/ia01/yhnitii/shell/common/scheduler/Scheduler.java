package com.ia01.yhnitii.shell.common.scheduler;

import com.ia01.yhnitii.shell.zip.service.CachedZipService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class Scheduler {

	@NonFinal
	@Value("${zip-cache.live-time}")
	Integer liveTime;

	CachedZipService cachedZipService;

	// every 5 minutes
	@Scheduled(fixedDelay = 300_000)
	public void checkInNotifications() {
		LocalDateTime dateTime = LocalDateTime.now().minus(liveTime, ChronoUnit.MINUTES);
		cachedZipService.deleteAllByCreatedDateBefore(dateTime);
	}

}
