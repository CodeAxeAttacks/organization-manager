# Organization Manager EAR Module

## What is this?

This module packages the EJB and WAR modules together into a single **Enterprise Archive (.ear)** file.

**Benefits:**
- ✅ Deploy **one file** instead of two
- ✅ Modules automatically share classloaders
- ✅ Standard Java EE approach
- ✅ Easier version management

---

## Structure

```
organization-manager.ear
├── organization-manager-ejb.jar   (EJB module with business logic)
├── organization-manager-web.war   (Web module with REST API)
└── META-INF/
    └── application.xml            (Deployment descriptor)
```

---

## Build

From the **parent directory** (`organization-manager/`):

```bash
mvn clean package
```

This will create:
```
organization-manager-ear/target/organization-manager.ear
```

---

## Deploy to WildFly

### Docker (Recommended)

```bash
# Copy to Node 1
docker cp organization-manager-ear/target/organization-manager.ear \
  wildfly-node1:/opt/jboss/wildfly/standalone/deployments/

# Copy to Node 2
docker cp organization-manager-ear/target/organization-manager.ear \
  wildfly-node2:/opt/jboss/wildfly/standalone/deployments/
```

### Local WildFly

```bash
cp organization-manager-ear/target/organization-manager.ear \
   $WILDFLY_HOME/standalone/deployments/
```

---

## Verify Deployment

```bash
# Check deployment status
docker exec wildfly-node1 ls -la /opt/jboss/wildfly/standalone/deployments/

# Should see:
# organization-manager.ear
# organization-manager.ear.deployed  <- Success!
```

---

## Test

Once deployed, the API is available at:

```bash
# Through HAProxy (port 8888)
curl -X POST http://localhost:8888/api/v1/manager/hire/1

# Direct to Node 1 (port 9080)
curl -X POST http://localhost:9080/api/v1/manager/hire/1

# Direct to Node 2 (port 9180)
curl -X POST http://localhost:9180/api/v1/manager/hire/1
```

---

## Troubleshooting

**Check logs:**
```bash
docker logs wildfly-node1 --tail 100
```

**Look for:**
- ✅ `Deployed "organization-manager.ear"`
- ❌ `WFLYCTL0013: Operation ("deploy") failed` - if you see this, check the full error

**Undeploy (if needed):**
```bash
docker exec wildfly-node1 rm /opt/jboss/wildfly/standalone/deployments/organization-manager.ear
```
