# frozen_string_literal: true

class DeliverableConstants

  ##################
  #### FIREBASE ####
  ##################

  # a gsp files directory
  def self.GSP_DIRECTORY
    './'
  end

  # a gsp file name for staging
  def self.GSP_STAGING
    './Surveys/Configurations/Plists/GoogleService/Staging/GoogleService-Info.plist'
  end

  # a gsp file name for production
  def self.GSP_PRODUCTION
    './Surveys/Configurations/Plists/GoogleService/Production/GoogleService-Info.plist'
  end

  # The path to the upload-symbols file of the Fabric app
  def self.BINARY_PATH
    './Pods/FirebaseCrashlytics/upload-symbols'
  end

  # a firebase app ID for Staging
  def self.FIREBASE_APP_ID_STAGING
    '1:713044215071:ios:eb121336a05ba0718adc2a'
  end

  # a firebase app ID for Production
  def self.FIREBASE_APP_ID_PRODUCTION
    '1:713044215071:ios:cdc7555a219bf6488adc2a'
  end

  # Firebase Tester group name, seperate by comma(,) string
  def self.FIREBASE_TESTER_GROUPS
    "nimble-dev"
  end

  #####################
  ### App Store API ###
  #####################

  # App Store Connect API Key ID
  def self.APP_STORE_KEY_ID
    '3Q76PWRQN6'
  end

  # App Store Connect API Issuer ID
  def self.APP_STORE_ISSUER_ID
    '69a6de82-b7cb-47e3-e053-5b8c7c11a4d1'
  end

end
