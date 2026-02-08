import { Grid, Paper, Typography, Box } from '@mui/material';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import CloudQueueIcon from '@mui/icons-material/CloudQueue';
import StorageIcon from '@mui/icons-material/Storage';
import ReceiptIcon from '@mui/icons-material/Receipt';
import { CostSummary } from '../types';

interface SummaryCardsProps {
  summary: CostSummary;
}

function SummaryCards({ summary }: SummaryCardsProps) {
  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: summary.currency,
      minimumFractionDigits: 2,
    }).format(amount);
  };

  const getTopProvider = () => {
    const providers = Object.entries(summary.costByProvider);
    if (providers.length === 0) return 'N/A';
    const top = providers.reduce((a, b) => (a[1] > b[1] ? a : b));
    return top[0];
  };

  const getTopService = () => {
    const services = Object.entries(summary.costByService);
    if (services.length === 0) return 'N/A';
    const top = services.reduce((a, b) => (a[1] > b[1] ? a : b));
    return top[0];
  };

  const cards = [
    {
      title: 'Total Cost',
      value: formatCurrency(summary.totalCost),
      icon: <AttachMoneyIcon sx={{ fontSize: 40 }} />,
      color: '#1976d2',
      bgColor: '#e3f2fd',
    },
    {
      title: 'Total Records',
      value: summary.totalRecords.toString(),
      icon: <ReceiptIcon sx={{ fontSize: 40 }} />,
      color: '#2e7d32',
      bgColor: '#e8f5e9',
    },
    {
      title: 'Top Provider',
      value: getTopProvider(),
      icon: <CloudQueueIcon sx={{ fontSize: 40 }} />,
      color: '#ed6c02',
      bgColor: '#fff3e0',
    },
    {
      title: 'Top Service',
      value: getTopService(),
      icon: <StorageIcon sx={{ fontSize: 40 }} />,
      color: '#9c27b0',
      bgColor: '#f3e5f5',
    },
  ];

  return (
    <Grid container spacing={3}>
      {cards.map((card, index) => (
        <Grid item xs={12} sm={6} md={3} key={index}>
          <Paper
            elevation={2}
            sx={{
              p: 3,
              display: 'flex',
              flexDirection: 'column',
              height: '100%',
              transition: 'transform 0.2s, box-shadow 0.2s',
              '&:hover': {
                transform: 'translateY(-4px)',
                boxShadow: 4,
              },
            }}
          >
            <Box display="flex" justifyContent="space-between" alignItems="flex-start">
              <Box>
                <Typography variant="subtitle2" color="text.secondary" gutterBottom>
                  {card.title}
                </Typography>
                <Typography variant="h4" fontWeight="bold" color={card.color}>
                  {card.value}
                </Typography>
              </Box>
              <Box
                sx={{
                  backgroundColor: card.bgColor,
                  color: card.color,
                  borderRadius: 2,
                  p: 1,
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'center',
                }}
              >
                {card.icon}
              </Box>
            </Box>
          </Paper>
        </Grid>
      ))}
    </Grid>
  );
}

export default SummaryCards;