CREATE TABLE `key` (
	`userId` text(8) NOT NULL,
	`provider_name` text NOT NULL,
	`provider_id` text NOT NULL,
	`secret` text,
	`verified` integer,
	PRIMARY KEY(`provider_id`, `provider_name`),
	FOREIGN KEY (`userId`) REFERENCES `user`(`id`) ON UPDATE no action ON DELETE cascade
);
--> statement-breakpoint
CREATE TABLE `session` (
	`id` text PRIMARY KEY NOT NULL,
	`user_id` text NOT NULL,
	`expires_at` integer NOT NULL,
	FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON UPDATE no action ON DELETE cascade
);
--> statement-breakpoint
CREATE TABLE `user` (
	`id` text PRIMARY KEY NOT NULL,
	`username` text NOT NULL
);
