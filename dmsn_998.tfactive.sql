# MySQL-Front 5.1  (Build 4.13)

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE */;
/*!40101 SET SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES */;
/*!40103 SET SQL_NOTES='ON' */;


# Host: 172.24.24.235    Database: dmsn_998
# ------------------------------------------------------
# Server version 5.5.48-log

#
# Source for table tfactive
#

CREATE TABLE `tfactive` (
  `fAlmSN` decimal(18,0) NOT NULL,
  `MOSN` decimal(9,0) NOT NULL,
  `fRMOSN` decimal(9,0) NOT NULL,
  `fPMOSN` decimal(9,0) NOT NULL,
  `fMOType` varchar(16) NOT NULL,
  `fAlarmKpi` char(10) NOT NULL DEFAULT '____',
  `fCause` varchar(256) NOT NULL DEFAULT '____',
  `fAlmType` varchar(32) DEFAULT NULL,
  `fSuppressed` char(1) DEFAULT 'N',
  `fAddInfo` varchar(512) DEFAULT NULL,
  `fSeverity` decimal(1,0) NOT NULL,
  `fStatus` varchar(8) NOT NULL DEFAULT 'Enable',
  `fTrend` varchar(8) NOT NULL,
  `fOccurTime` char(19) NOT NULL,
  `fLastTime` char(19) NOT NULL,
  `fOpStatus` varchar(32) DEFAULT NULL,
  `fCount` decimal(9,0) NOT NULL DEFAULT '1',
  `fDetail` varchar(10240) DEFAULT NULL,
  `fOriginSN` varchar(64) DEFAULT NULL,
  `fOriginInfo` varchar(1024) DEFAULT NULL,
  `fUpgradeCount` decimal(9,0) DEFAULT NULL,
  `fOriginSeverity` decimal(1,0) DEFAULT NULL,
  `fClearTime` char(19) DEFAULT NULL,
  `fConfirmTime` char(19) DEFAULT NULL,
  `fConfirmUserID` varchar(32) DEFAULT NULL,
  `fDelTime` char(19) DEFAULT NULL,
  `fDelUserID` varchar(32) DEFAULT NULL,
  `fRelatedKpi` char(5) DEFAULT NULL,
  `fRelatedMOSN` decimal(9,0) DEFAULT NULL,
  `fDataTime` char(19) DEFAULT NULL,
  `fDataValue` varchar(256) DEFAULT NULL,
  `fMOIP` varchar(64) DEFAULT NULL,
  `fMOAlias` varchar(256) DEFAULT NULL,
  `fCauseAlias` varchar(512) DEFAULT NULL,
  `fHealth` decimal(9,0) NOT NULL DEFAULT '0',
  `fIsNotify` char(1) DEFAULT 'N',
  `fAlmLocator` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`fAlmSN`),
  KEY `AK_sevAndOtimeOfActive` (`fSeverity`,`fOccurTime`),
  KEY `AK_rmosnOfActive` (`fRMOSN`),
  KEY `AK_mosnOfActive` (`MOSN`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

#
# Source for trigger ins_tfhis
#

CREATE DEFINER='root'@'localhost' TRIGGER `dmsn_998`.`ins_tfhis` AFTER DELETE ON `dmsn_998`.`tfactive`
  FOR EACH ROW begin
if old.FCONFIRMTIME is null then
insert into tfhis(FALMSN, MOSN, FALARMKPI, FCAUSE, FALMTYPE, FSUPPRESSED, FADDINFO, FSEVERITY, FSTATUS, FTREND, FOCCURTIME, FLASTTIME, FOPSTATUS, FCOUNT, FDETAIL, FORIGINSN, FORIGININFO, FUPGRADECOUNT, FORIGINSEVERITY, FCLEARTIME, FCONFIRMTIME, FCONFIRMUSERID, FDELTIME, FDELUSERID, FMOTYPE, fRelatedKpi, fRelatedMOSN, fDataTime, fDataValue,fRMOSN,fPMOSN,fMOIP,fMOAlias,fCauseAlias,fHealth)
values (old.FALMSN, old.MOSN, old.FALARMKPI, old.FCAUSE, old.FALMTYPE, old.FSUPPRESSED, old.FADDINFO, old.FSEVERITY, old.FSTATUS, old.FTREND, old.FOCCURTIME, old.FLASTTIME, old.FOPSTATUS, old.FCOUNT, old.FDETAIL, old.FORIGINSN, old.FORIGININFO, old.FUPGRADECOUNT, old.FORIGINSEVERITY, old.FCLEARTIME, old.FCONFIRMTIME, old.FCONFIRMUSERID, old.FDELTIME, old.FDELUSERID, old.FMOTYPE, old.fRelatedKpi, old.fRelatedMOSN, old.fDataTime, old.fDataValue,old.fRMOSN,old.FPMOSN,old.fMOIP,old.fMOAlias,old.fCauseAlias,old.fHealth);
DELETE from tfEvt where fAlmSN=old.fAlmSN;
else
insert into tfhis(FALMSN, MOSN, FALARMKPI, FCAUSE, FALMTYPE, FSUPPRESSED, FADDINFO, FSEVERITY, FSTATUS, FTREND, FOCCURTIME, FLASTTIME, FOPSTATUS, FCOUNT, FDETAIL, FORIGINSN, FORIGININFO, FUPGRADECOUNT, FORIGINSEVERITY, FCLEARTIME, FCONFIRMTIME, FCONFIRMUSERID, FDELTIME, FDELUSERID, FMOTYPE, fRelatedKpi, fRelatedMOSN, fDataTime, fDataValue,fRMOSN,fPMOSN,fMOIP,fMOAlias,fCauseAlias,fHealth)
values (old.FALMSN, old.MOSN, old.FALARMKPI, old.FCAUSE, old.FALMTYPE, old.FSUPPRESSED, old.FADDINFO, old.FSEVERITY, old.FSTATUS, old.FTREND, old.FOCCURTIME, old.FLASTTIME, old.FOPSTATUS, old.FCOUNT, old.FDETAIL, old.FORIGINSN, old.FORIGININFO, old.FUPGRADECOUNT, old.FORIGINSEVERITY, old.FCLEARTIME, old.FCONFIRMTIME, old.FCONFIRMUSERID, old.FDELTIME, old.FDELUSERID, old.FMOTYPE, old.fRelatedKpi, old.fRelatedMOSN, old.fDataTime, old.fDataValue,old.fRMOSN,old.FPMOSN,old.fMOIP,old.fMOAlias,old.fCauseAlias,old.fHealth);
DELETE from tfEvt where fAlmSN=old.fAlmSN;
end if;
end;


#
# Source for trigger inserttfactive
#

CREATE DEFINER='root'@'localhost' TRIGGER `dmsn_998`.`inserttfactive` BEFORE INSERT ON `dmsn_998`.`tfactive`
  FOR EACH ROW Begin

IF (select fHealth from tfcause2health where ID=new.MOSN and fCategory='M' and fCause=new.fCause) is not null
THEN
set new.fHealth=(select fhealth from tfcause2health where ID=new.MOSN && fCause=new.fCause);
else
IF (select fCause from tfcause2health where fCause=new.fCause && fCategory='C') is not null then
set new.fHealth=(select fhealth from tfcause2health where fCause=new.fCause and fCategory='C');
else
set new.fHealth='2';
END IF;
END IF;
end;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
