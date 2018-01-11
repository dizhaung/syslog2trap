# syslog2trap

需要增加两个mysql trigger，如下
CREATE
    TRIGGER `store_alarm_when_insert_tr` AFTER INSERT ON `tfactive` 
    FOR EACH ROW 
BEGIN
	INSERT  INTO tfactive_alarm(fAlmSN,MOSN,fRMOSN,fPMOSN,fMOType,fAlarmKpi,fCause,fAlmType,fSuppressed,fAddInfo,fSeverity,fStatus,fTrend,fOccurTime,fLastTime,fOpStatus,fCount,fDetail,fOriginSN,fOriginInfo,fUpgradeCount,fOriginSeverity,fClearTime,fConfirmTime,fConfirmUserID,fDelTime,fDelUserID,fRelatedKpi,fRelatedMOSN,fDataTime,fDataValue,fMOIP,fMOAlias,fCauseAlias,fHealth,fIsNotify,fAlmLocator) 
	VALUES(NEW.fAlmSN,NEW.MOSN,NEW.fRMOSN,NEW.fPMOSN,NEW.fMOType,NEW.fAlarmKpi,NEW.fCause,NEW.fAlmType,NEW.fSuppressed,NEW.fAddInfo,NEW.fSeverity,NEW.fStatus,NEW.fTrend,NEW.fOccurTime,NEW.fLastTime,NEW.fOpStatus,NEW.fCount,NEW.fDetail,NEW.fOriginSN,NEW.fOriginInfo,NEW.fUpgradeCount,NEW.fOriginSeverity,NEW.fClearTime,NEW.fConfirmTime,NEW.fConfirmUserID,NEW.fDelTime,NEW.fDelUserID,NEW.fRelatedKpi,NEW.fRelatedMOSN,NEW.fDataTime,NEW.fDataValue,NEW.fMOIP,NEW.fMOAlias,NEW.fCauseAlias,NEW.fHealth,NEW.fIsNotify,NEW.fAlmLocator);
		
END;


CREATE
    TRIGGER `store_alarm_when_update_tr` AFTER UPDATE ON `tfactive` 
    FOR EACH ROW 
BEGIN
	INSERT  INTO tfactive_alarm(fAlmSN,MOSN,fRMOSN,fPMOSN,fMOType,fAlarmKpi,fCause,fAlmType,fSuppressed,fAddInfo,fSeverity,fStatus,fTrend,fOccurTime,fLastTime,fOpStatus,fCount,fDetail,fOriginSN,fOriginInfo,fUpgradeCount,fOriginSeverity,fClearTime,fConfirmTime,fConfirmUserID,fDelTime,fDelUserID,fRelatedKpi,fRelatedMOSN,fDataTime,fDataValue,fMOIP,fMOAlias,fCauseAlias,fHealth,fIsNotify,fAlmLocator) 
	VALUES(NEW.fAlmSN,NEW.MOSN,NEW.fRMOSN,NEW.fPMOSN,NEW.fMOType,NEW.fAlarmKpi,NEW.fCause,NEW.fAlmType,NEW.fSuppressed,NEW.fAddInfo,NEW.fSeverity,NEW.fStatus,NEW.fTrend,NEW.fOccurTime,NEW.fLastTime,NEW.fOpStatus,NEW.fCount,NEW.fDetail,NEW.fOriginSN,NEW.fOriginInfo,NEW.fUpgradeCount,NEW.fOriginSeverity,NEW.fClearTime,NEW.fConfirmTime,NEW.fConfirmUserID,NEW.fDelTime,NEW.fDelUserID,NEW.fRelatedKpi,NEW.fRelatedMOSN,NEW.fDataTime,NEW.fDataValue,NEW.fMOIP,NEW.fMOAlias,NEW.fCauseAlias,NEW.fHealth,NEW.fIsNotify,NEW.fAlmLocator);
  
END;
