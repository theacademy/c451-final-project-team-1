INSERT INTO `smart_recipe_finder`.`diets` (`id`, `name`) VALUES
  (1, 'Vegetarian'),
  (2, 'Vegan'),
  (3, 'Gluten-Free'),
  (4, 'Keto'),
  (5, 'Paleo'),
  (6, 'Pescatarian');

INSERT INTO `smart_recipe_finder`.`users` (`id`, `username`, `password`, `email`) VALUES
  (1, 'alice', 'password123', 'alice@example.com'),
  (2, 'bob', 'securepass', 'bob@example.com'),
  (3, 'carla', 'veggie2026', 'carla@example.com'),
  (4, 'daniel', 'paleoLife!', 'daniel@example.net'),
  (5, 'emma', 'fishlover', 'emma@example.org');

INSERT INTO `smart_recipe_finder`.`dietary_restrictions` (`user_id`, `diet_id`) VALUES
  (1, 1),
  (1, 3),
  (2, 4),
  (3, 2),
  (4, 5),
  (5, 6);

INSERT INTO `smart_recipe_finder`.`households` (`id`, `code`, `nickname`, `address`) VALUES
  (1, 'A1B2C3D4', 'Smith Family', '123 Maple Lane'),
  (2, 'E5F6G7H8', 'Solo Renter', '502 Elm St Apt 4B'),
  (3, 'J9K0L1M2', 'Weekend Crew', '19 Pine Loop');

INSERT INTO `smart_recipe_finder`.`household_memberships` (`household_id`, `user_id`, `date_added`) VALUES
  (1, 1, '2026-05-01 09:15:00'),
  (1, 2, '2026-05-01 09:20:00'),
  (2, 3, '2026-05-10 18:30:00'),
  (3, 4, '2026-05-18 07:45:00'),
  (3, 5, '2026-05-18 07:50:00');

INSERT INTO `smart_recipe_finder`.`ingredients` (`id`, `name`) VALUES
  (1, 'Milk'),
  (2, 'Eggs'),
  (3, 'Flour'),
  (4, 'Chicken Breast'),
  (5, 'Salmon Fillet'),
  (6, 'Rice'),
  (7, 'Almonds'),
  (8, 'Gluten-Free Pasta'),
  (9, 'Tofu'),
  (10, 'Spinach'),
  (11, 'Yogurt'),
  (12, 'Olive Oil');

INSERT INTO `smart_recipe_finder`.`intolerances` (`user_id`, `item_id`) VALUES
  (1, 1),
  (1, 3),
  (2, 2),
  (3, 4),
  (3, 5),
  (4, 3);

INSERT INTO `smart_recipe_finder`.`inventory` (`id`, `household_id`, `item_id`, `quantity`, `unit`, `date_stored`, `expiration`) VALUES
  (1, 1, 1, 2.00, 'L', '2026-05-20 08:00:00', '2026-06-02'),
  (2, 1, 2, 12.00, 'pcs', '2026-05-20 08:05:00', '2026-05-28'),
  (3, 1, 3, 1.50, 'kg', '2026-05-20 08:10:00', '2026-06-15'),
  (4, 1, 4, 1.20, 'kg', '2026-05-20 08:20:00', '2026-05-25'),
  (5, 1, 10, 0.50, 'kg', '2026-05-20 08:25:00', '2026-05-30'),
  (6, 2, 9, 0.80, 'kg', '2026-05-22 11:00:00', '2026-06-05'),
  (7, 2, 8, 0.90, 'kg', '2026-05-22 11:05:00', '2026-07-01'),
  (8, 2, 6, 1.00, 'kg', '2026-05-22 11:10:00', '2026-06-20'),
  (9, 2, 10, 0.25, 'kg', '2026-05-22 11:15:00', '2026-05-26'),
  (10, 2, 12, 0.75, 'L', '2026-05-22 11:20:00', NULL),
  (11, 3, 5, 0.90, 'kg', '2026-05-24 14:00:00', '2026-06-08'),
  (12, 3, 7, 0.30, 'kg', '2026-05-24 14:05:00', '2026-09-01'),
  (13, 3, 11, 0.50, 'kg', '2026-05-24 14:10:00', '2026-06-04'),
  (14, 3, 12, 0.60, 'L', '2026-05-24 14:15:00', NULL);

INSERT INTO `smart_recipe_finder`.`recipes` (`id`, `title`, `prep_time_in_mins`, `instructions`, `image_link`) VALUES
  (1, 'Chicken Stir Fry', 35, 'Marinate chicken, chop vegetables, stir fry with soy sauce and serve over rice.', 'https://example.com/images/chicken-stir-fry.jpg'),
  (2, 'Vegan Buddha Bowl', 25, 'Roast sweet potato, cook quinoa, add chickpeas and fresh greens, drizzle tahini dressing.', 'https://example.com/images/vegan-buddha-bowl.jpg'),
  (3, 'Gluten-Free Pancakes', 20, 'Mix gluten-free flour with almond milk, eggs, and bake on a griddle until golden brown.', 'https://example.com/images/gluten-free-pancakes.jpg'),
  (4, 'Salmon with Lemon Rice', 40, 'Pan-sear salmon, cook rice with lemon zest, serve with steamed greens and a light vinaigrette.', 'https://example.com/images/salmon-lemon-rice.jpg'),
  (5, 'Keto Avocado Egg Salad', 15, 'Mix hard-boiled eggs with avocado, mustard, and herbs, serve over mixed greens.', 'https://example.com/images/keto-avocado-egg-salad.jpg'),
  (6, 'Paleo Beef Stew', 120, 'Slow cook beef with root vegetables, bone broth, and herbs until tender and rich.', 'https://example.com/images/paleo-beef-stew.jpg'),
  (7, 'Mediterranean Pasta', 30, 'Toss pasta with olives, tomatoes, feta, spinach, and olive oil for a fresh dinner.', 'https://example.com/images/mediterranean-pasta.jpg'),
  (8, 'Yogurt Parfait', 10, 'Layer yogurt with granola, berries, and honey for a quick breakfast or snack.', 'https://example.com/images/yogurt-parfait.jpg');

INSERT INTO `smart_recipe_finder`.`saved_recipes` (`user_id`, `recipe_id`, `date_saved`) VALUES
  (1, 1, '2026-05-21 10:30:00'),
  (1, 3, '2026-05-21 10:35:00'),
  (1, 8, '2026-05-21 10:40:00'),
  (2, 5, '2026-05-22 12:15:00'),
  (2, 6, '2026-05-22 12:20:00'),
  (3, 2, '2026-05-23 19:05:00'),
  (3, 3, '2026-05-23 19:10:00'),
  (4, 6, '2026-05-24 08:00:00'),
  (4, 7, '2026-05-24 08:05:00'),
  (5, 4, '2026-05-25 14:45:00'),
  (5, 7, '2026-05-25 14:50:00'),
  (5, 8, '2026-05-25 14:55:00');
