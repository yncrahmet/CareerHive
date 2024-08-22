import db.Database;
import model.*;
import repository.*;

import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) {

        // Create table

        PlanRepository.createPlanTable();
        UserRepository.createUserTable();
        FeatureRepository.createFeatureTable();
        LimitRepository.createLimitTable();
        UserFeatureLimitRepository.createUserFeatureLimitTable();
        UserSubsciptionRepository.createUserFeatureLimitTable();

        Timestamp dateNow = new Timestamp(System.currentTimeMillis());

        // User

        UserRepository userRepository = new UserRepository();

        User user = new User("Okan", "bfa7951d-d166-494a-8fa9-6f1a7aacc303","okan@archisacademy.com", dateNow);
        User userTwo = new User("Emre","b6e7c769-f056-4f57-8265-5755377ad611","emre@archisacademy.com",dateNow);

        userRepository.save(user);
        userRepository.save(userTwo);

        // Plan

        PlanRepository planRepository = new PlanRepository();

        Plan plan = new Plan("gold","this is a gold package for acme",
                "3a8d83ac-6709-4dcc-9d99-6e13109e5f2f", dateNow,"Thoufeeque Saheer");

        Plan planTwo = new Plan("silver","this is a silver package",
                "280d13bb-05aa-4dff-9edf-e4e8d2619291",dateNow,"Thoufeeque Saheer");

        Plan planThree = new Plan("bronze","this is a bronze package for Acme",
                "cc54bee3-0d57-4a80-bc52-e282887b4f49",dateNow,"Thoufeeque Saheer");

        planRepository.save(plan);
        planRepository.save(planTwo);
        planRepository.save(planThree);

        // Feature

        FeatureRepository featureRepository = new FeatureRepository();
        Feature feature = new Feature("trainer-session","this is the feature for trainers",dateNow,
                "Thoufeeque Saheer","b1870625-c1fc-44dd-b52d-794d3ae583d0");

        Feature featureTwo = new Feature("mentor-session","this is a mentor session feature",dateNow,
                "Thoufeeque Saheer","86e89c6a-98c2-43a4-9864-2177efbe3b64");

        featureRepository.save(feature);
        featureRepository.save(featureTwo);

        // Limit

        LimitRepository limitRepository = new LimitRepository();

        Limit limit = new Limit(1,1,10,dateNow,"Thoufeeque Saheer");
        Limit limitTwo = new Limit(1,2,10,dateNow,"Thoufeeque Saheer");
        Limit limitThree = new Limit(2,1,5,dateNow,"Thoufeeque Saheer");
        Limit limitFour = new Limit(2,2,5,dateNow,"Thoufeeque Saheer");
        Limit limitFive = new Limit(3,1,1,dateNow,"Thoufeeque Saheer");
        Limit limitSix = new Limit(3,1,1,dateNow,"Thoufeeque Saheer");

        limitRepository.save(limit);
        limitRepository.save(limitTwo);
        limitRepository.save(limitThree);
        limitRepository.save(limitFour);
        limitRepository.save(limitFive);
        limitRepository.save(limitSix);

        // UserFeatureLimit

        UserFeatureLimitRepository uflRepository = new UserFeatureLimitRepository();

        UserFeatureLimit ufl = new UserFeatureLimit("793b0a49-1bdd-475b-9819-284e0b1f22e7",1,3,dateNow,
                "Thoufeeque Saheer",1 );

        UserFeatureLimit uflTwo = new UserFeatureLimit("110964cb-2f19-4660-a102-f0bbb0c2c9e5",2,0,dateNow,
                "Thoufeeque Saheer",1 );

        uflRepository.save(ufl);
        uflRepository.save(uflTwo);

        // UserSubscription

        Timestamp endDate = Timestamp.valueOf(dateNow.toLocalDateTime().plusYears(1));
        Timestamp renewalDate = Timestamp.valueOf(dateNow.toLocalDateTime().plusMonths(1));

        Timestamp endDateTwo = Timestamp.valueOf(dateNow.toLocalDateTime().plusMonths(3));
        Timestamp renewalDateTwo = Timestamp.valueOf(dateNow.toLocalDateTime().plusMonths(3));

        UserSubsciptionRepository userSubsRepo = new UserSubsciptionRepository();

        UserSubscription userSubs = new UserSubscription("6fdc0b59-8a3e-490e-b232-8610565cd8de",1,1,
                dateNow,endDate,renewalDate,"Active",dateNow,"Thoufeeque Saheer");

        UserSubscription userSubsTwo = new UserSubscription("18cf3506-2de5-473f-83de-c6afe4be6df9",2,3,
                dateNow,endDateTwo,renewalDateTwo,"Deactive",dateNow,"Thoufeeque Saheer");

        userSubsRepo.save(userSubs);
        userSubsRepo.save(userSubsTwo);

        // QUERY

        // How you can find a not active subscribers ?
        userSubsRepo.findNotActiveSubs();

        // How can you find the limits of a subscriber and provide an output with user details and limits.
        userSubsRepo.findSubscriberLimit(1);

        // I need to update a plan limits of a feature.
        limitRepository.updatePlanLimitForFeature(5, 1,1);

        // I want to delete a plan which has already subscribers.
        planRepository.deletePlanById(1);

        // Find the subscribers with starter plan but starting from beginning of the year  2024
        userSubsRepo.findSubsWithBronzePlanFrom2024();
    }
}
