export const handler = async (event, context, callback) => {
    event.response = {
      claimsOverrideDetails: {
        claimsToAddOrOverride: {
          role: 'BASIC_USER',
        },
      },
    };
  
    callback(null, event);
  };
  