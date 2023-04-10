import React from 'react'

export const currentUserInfo = atom<FirebaseAuthTypes.User | undefined | null>({
  key: "currentUserInfo",
  default: undefined,
  dangerouslyAllowMutability: true,
});
